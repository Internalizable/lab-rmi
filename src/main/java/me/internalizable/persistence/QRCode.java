package me.internalizable.persistence;

import dev.morphia.Datastore;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.io.Serializable;

@Entity("qrcodes")
@Getter @Setter
public class QRCode implements Serializable {
    @Id
    private ObjectId id;
    private String code;
    private boolean used;
    private String email;

    @Reference
    private Country country;

    public QRCode() {}

    public void save(Datastore datastore) {
        datastore.save(this);
    }

    public void delete(Datastore datastore) {
        datastore.delete(this);
    }
}