package com.D2D.ShortUrl.Repository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
@Repository
public class UUIDGenerator {
   private  UUID uuid = UUID.randomUUID();

    public UUIDGenerator() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
