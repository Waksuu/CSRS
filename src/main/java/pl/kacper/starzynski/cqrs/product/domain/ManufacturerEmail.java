package pl.kacper.starzynski.cqrs.product.domain;

class ManufacturerEmail {
    private final String manufacturerEmail;

    ManufacturerEmail(String manufacturerEmail) {
        this.manufacturerEmail = manufacturerEmail;
    }

    static ManufacturerEmail create(String manufacturerEmail) {
        return new ManufacturerEmail(manufacturerEmail);
    }
}
