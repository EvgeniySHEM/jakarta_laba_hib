package ru.sanctio.jakarta_laba_hib.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

import java.io.Serializable;

import java.time.LocalDate;
import java.util.*;
import java.sql.Date;

@Entity
@XmlRootElement
@Table(name = "client", schema = "public", catalog = "jakarta")
@NamedQueries({
        @NamedQuery(name = "Client.All", query = "select c from ClientEntity c")
})
//@NamedNativeQueries({
//        @NamedQuery()
//})
public class ClientEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "clientid")
    private int clientId;
    @Basic
    @Column(name = "clientname")
    private String clientName;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "added")
    private Date added;
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<AddressEntity> addresses = new ArrayList<>();

    public ClientEntity() {
    }

    public ClientEntity(String clientName, String type, String added) {
        setClientName(clientName);
        setType(type);
        setAdded(added);
    }

    public ClientEntity(int clientId, String clientName, String type, String added) {
        this(clientName, type, added);
        setClientId(clientId);
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientid) {
        this.clientId = clientid;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        if(clientName == null || clientName.trim().isEmpty()) {
            throw new IllegalArgumentException("The client name value cannot be empty");
        }
        if (clientName.length() > 100)
            throw new IllegalArgumentException("The client name length should not exceed 100 characters");
        if (clientName.matches("^[а-яёА-ЯЁ{\\-\\s,.}]+$")) {
            this.clientName = clientName;
        } else {
            throw new IllegalArgumentException("For the client name field, it is allowed\n" +
                    "to use only the Russian alphabet, as well\n" +
                    "as the characters {- ,.}");
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        Objects.requireNonNull(type, "The type value cannot be empty");
        this.type = type;
    }

    public String getAdded() {
        if(added == null) {
            LocalDate date = LocalDate.now();
            added = Date.valueOf(date);
        }
        return added.toLocalDate().toString();
    }

    public void setAdded(String date) {
        LocalDate localDate = LocalDate.parse(date);
        if (localDate.isBefore(LocalDate.EPOCH) || localDate.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("You entered an incorrect year");
//        this.added = Date.valueOf(localDate);
//        this.added = localDate;
        this.added = Date.valueOf(date);
    }

    public List<AddressEntity> getAddresses() {
        return addresses;
    }

    public void addAddress(AddressEntity... address) {
        for (AddressEntity ex : address) {
            addresses.add(ex);
            ex.setClient(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientEntity that = (ClientEntity) o;

        if (clientId != that.clientId) return false;
        if (clientName != null ? !clientName.equals(that.clientName) : that.clientName != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (added != null ? !added.equals(that.added) : that.added != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = clientId;
        result = 31 * result + (clientName != null ? clientName.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (added != null ? added.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ClientEntity{" +
                "clientid=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", type='" + type + '\'' +
                ", added=" + added +
                '}';
    }
}
