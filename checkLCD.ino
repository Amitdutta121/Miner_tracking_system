#include <LiquidCrystal_I2C.h>
// Construct an LCD object and pass it the
// I2C address, width (in characters) and
// height (in characters). Depending on the
// Actual device, the IC2 address may change.
LiquidCrystal_I2C lcd(0x27, 16, 2);

void setup() {
 lcd.begin(16,2);     // The begin call takes the width and height. This
 lcd.init();          // Should match the number provided to the constructor.
 lcd.backlight();     // Turn on the backlight.

 lcd.print("CSE461");  // Print HELLO to the screen, starting at 0,0.
}

void loop() {
}