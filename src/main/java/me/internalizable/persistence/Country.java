package me.internalizable.persistence;

import dev.morphia.Datastore;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.io.Serializable;

@Entity("country")
@Getter @Setter
public class Country implements Serializable {
    @Id
    private ObjectId id;
    private String iso;
    private String name;
    private String image;
    private boolean regional;

    public Country() {}

    public void save(Datastore datastore) {
        datastore.save(this);
    }

    public void delete(Datastore datastore) {
        datastore.delete(this);
    }
}
