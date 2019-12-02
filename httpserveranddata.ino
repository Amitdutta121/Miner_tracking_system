#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>
#include <math.h>
#include <LiquidCrystal_I2C.h>

#include <Wire.h>
#include <SPI.h>
#include <Adafruit_BMP280.h>

#define cInput A0
#define cSelectPin D6
int inputValue1;
int inputValue2;


#define BMP_SCK  (13)
#define BMP_MISO (12)
#define BMP_MOSI (11)
#define BMP_CS   (10)

#define WIFI_DELAY        500
/* Max SSID octets. */
#define MAX_SSID_LEN      32
/* Wait this much until device gets IP. */
#define MAX_CONNECT_TIME  30000

Adafruit_BMP280 bmp; // I2C
//Adafruit_BMP280 bmp(BMP_CS); // hardware SPI
//Adafruit_BMP280 bmp(BMP_CS, BMP_MOSI, BMP_MISO,  BMP_SCK);


LiquidCrystal_I2C lcd(0x27, 16, 2);
const int AoutPin = A0;
// Replace with your network credentials
char ssid[MAX_SSID_LEN] = "";
String payloadCheck = "";
String alert = "";

// servername, message server and alertServer data
const char* serverName = "http://192.168.1.103/dataserver/postdata.php";
const char* messageServer = "http://192.168.1.103/dataserver/getmessage.php";
const char* alertServer = "http://192.168.1.103/dataserver/getalert.php";

// Keep this API Key value to be compatible with the PHP code provided in the project page.
// If you change the apiKeyValue value, the PHP file /postdata.php also needs to have the same key
String apiKeyValue = "tPmAT5Ab3j7F9";
int value;


void setup() {

  Serial.begin(115200);
  pinMode(cSelectPin, OUTPUT);
  scanAndConnect();

  Serial.println(F("BMP280 test"));

  if (!bmp.begin(0x76)) {
    Serial.println(F("Could not find a valid BMP280 sensor, check wiring!"));
    while (1);
  }
  bmp.setSampling(Adafruit_BMP280::MODE_NORMAL,     /* Operating Mode. */
                  Adafruit_BMP280::SAMPLING_X2,     /* Temp. oversampling */
                  Adafruit_BMP280::SAMPLING_X16,    /* Pressure oversampling */
                  Adafruit_BMP280::FILTER_X16,      /* Filtering. */
                  Adafruit_BMP280::STANDBY_MS_500); /* Standby time. */

  lcd.begin(16,2);     // The begin call takes the width and height. This
  lcd.init();          // Should match the number provided to the constructor.
  lcd.backlight();     // Turn on the backlight.

  pinMode(D3, OUTPUT);
  pinMode(AoutPin,OUTPUT);

}

void loop() {

  digitalWrite(cSelectPin, LOW);
  inputValue1 = analogRead(cInput);


  digitalWrite(cSelectPin, HIGH);
  inputValue2 = analogRead(cInput);

  long rssi = WiFi.RSSI();
  //Check WiFi connection status
  if(WiFi.status()== WL_CONNECTED){
    HTTPClient http;

    // Your Domain name with URL path or IP address with path
    http.begin(serverName);

    // Specify content-type header
    http.addHeader("Content-Type", "application/x-www-form-urlencoded");

    // Prepare your HTTP POST request data
  String httpRequestData = "api_key=" + apiKeyValue + "&umac=" + String(WiFi.macAddress())
                          + "&rmac=" + String(WiFi.SSID()) + "&distance=" + rssi + "&data=" + String(bmp.readTemperature())+","+String(bmp.readPressure())+","+String(bmp.readAltitude(1013.25))+","+String(inputValue1)+","+String(inputValue2);
    Serial.print("httpRequestData: ");
    Serial.println(httpRequestData);

    // You can comment the httpRequestData variable above
    // then, use the httpRequestData variable below (for testing purposes without the BME280 sensor)
    //String httpRequestData = "api_key=tPmAT5Ab3j7F9&sensor=BME280&location=Office&value1=24.75&value2=49.54&value3=1005.14";

    // Send HTTP POST request
    int httpResponseCode = http.POST(httpRequestData);


    if (httpResponseCode>0) {
      Serial.print("HTTP Response code: ");
      Serial.println(httpResponseCode);
    }
    else {
      Serial.print("Error code: ");
      Serial.println(httpResponseCode);
    }
    // Free resources
    http.end();

    http.begin(messageServer);
    int httpCode = http.GET();            //Send the request
    String payload = http.getString();    //Get the response payload

    Serial.println(payload);    //Print request response payload

    if(payloadCheck != payload){
      lcd.clear();
      lcd.print(payload);
     }else{
        payloadCheck = payload;
      }



  http.end();  //Close connection

  http.begin(alertServer);
    int rCode = http.GET();            //Send the request
    String alertStatus = http.getString();    //Get the response payload

    Serial.println(alertStatus);

    if(alertStatus == "0"){
      digitalWrite(D3, LOW);
    }else{

      digitalWrite(D3, HIGH);
    }




  http.end();  //Close connection



  }
  else {
    Serial.println("WiFi Disconnected");
  }
  //Send an HTTP POST request every 30 seconds
  delay(5000);
}
void scanAndSort() {
  memset(ssid, 0, MAX_SSID_LEN);
  int n = WiFi.scanNetworks();
  Serial.println("Scan complete!");
  if (n == 0) {
    Serial.println("No networks available.");
  } else {
    Serial.print(n);
    Serial.println(" networks discovered.");
    int indices[n];
    for (int i = 0; i < n; i++) {
      indices[i] = i;
    }
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        if (WiFi.RSSI(indices[j]) > WiFi.RSSI(indices[i])) {
          std::swap(indices[i], indices[j]);
        }
      }
    }
    for (int i = 0; i < n; ++i) {
      Serial.println("The strongest open network is:");
      Serial.print(WiFi.SSID(indices[i]));
      Serial.print(" ");
      Serial.print(WiFi.RSSI(indices[i]));
      Serial.print(" ");
      Serial.print(WiFi.encryptionType(indices[i]));
      Serial.println();
      if(WiFi.encryptionType(indices[i]) == ENC_TYPE_NONE) {
        memset(ssid, 0, MAX_SSID_LEN);
        strncpy(ssid, WiFi.SSID(indices[i]).c_str(), MAX_SSID_LEN);
        break;
      }
    }
  }
}
void scanAndConnect(){
  Serial.println("Scanning for open networks...");
   if(WiFi.status() != WL_CONNECTED) {
    /* Clear previous modes. */
    WiFi.softAPdisconnect();
    WiFi.disconnect();
    WiFi.mode(WIFI_STA);
    delay(WIFI_DELAY);
    /* Scan for networks to find open guy. */
    scanAndSort();
    delay(WIFI_DELAY);
    /* Global ssid param need to be filled to connect. */
    if(strlen(ssid) > 0) {
      Serial.print("Connecting to ");
      Serial.println(ssid);
      /* No pass for WiFi. We are looking for non-encrypteds. */
      WiFi.begin(ssid);
      unsigned short try_cnt = 0;
      /* Wait until WiFi connection but do not exceed MAX_CONNECT_TIME */
      while (WiFi.status() != WL_CONNECTED && try_cnt < MAX_CONNECT_TIME / WIFI_DELAY) {
        delay(WIFI_DELAY);
        Serial.print(".");
        try_cnt++;
      }
      if(WiFi.status() == WL_CONNECTED) {
        Serial.println("");
        Serial.println("Connection Successful!");
        Serial.println("Your device IP address is ");
        Serial.println(WiFi.localIP());
      } else {
        Serial.println("Connection FAILED");
      }
    } else {
      Serial.println("No open networks available. :-(");
    }
  }
  }