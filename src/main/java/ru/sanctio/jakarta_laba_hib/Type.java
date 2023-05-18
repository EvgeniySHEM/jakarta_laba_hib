package ru.sanctio.jakarta_laba_hib;

public enum Type {
    TYPE1("Юридическое лицо"),
    TYPE2("Физическое лицо");

    private String clientType;

    Type(String clientType) {
        setClientType(clientType);
    }

    public String getClientType() {
        return clientType;
    }

    private void setClientType(String clientType) {
        if(clientType.length() > 20)
            throw new IllegalArgumentException("The clientType length should not exceed 25 characters");
        this.clientType = clientType;
    }
}
