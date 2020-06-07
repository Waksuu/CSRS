package pl.kacper.starzynski.cqrs.product.domain;

class ManufacturerEmail {
    private String manufacturerEmail;

    private ManufacturerEmail() {

    }

    ManufacturerEmail(String manufacturerEmail) {
        this.manufacturerEmail = manufacturerEmail;
    }

    static ManufacturerEmail create(String manufacturerEmail) {
        return new ManufacturerEmail(manufacturerEmail);
    }
}
