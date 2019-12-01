package sample;

public class Data {
    String data_id;
    String user_mac_address;
    String router_mac_address;
    String distance;
    String data;

    public Data(String id, String user_mac_address, String router_mac_address, String data) {
        this.data_id = id;
        this.user_mac_address = user_mac_address;
        this.router_mac_address = router_mac_address;
        this.data = data;
    }

    public String getId() {
        return data_id;
    }

    public void setId(String id) {
        this.data_id = id;
    }

    public String getUser_mac_address() {
        return user_mac_address;
    }

    public void setUser_mac_address(String user_mac_address) {
        this.user_mac_address = user_mac_address;
    }

    public String getRouter_mac_address() {
        return router_mac_address;
    }

    public void setRouter_mac_address(String router_mac_address) {
        this.router_mac_address = router_mac_address;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
