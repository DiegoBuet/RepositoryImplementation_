
public class Country extends Element {
    private String isoCode;

    public Country(String name, String isoCode) {
        super(name);
        this.isoCode = isoCode;
    }

    public String getIsoCode() {
        return isoCode;
    }

    @Override
    public String getType() {
        return "Country";
    }
    @Override
    public String toString() {
        return "Country: " + getName() + ", ISO Code: " + isoCode;
    }
}