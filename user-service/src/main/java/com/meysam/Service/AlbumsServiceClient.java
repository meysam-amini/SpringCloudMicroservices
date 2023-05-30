package com.meysam.Service;

import com.meysam.Model.AlbumEntity;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "albums-ws",fallbackFactory = albumsFallBackFactory.class)
public interface AlbumsServiceClient {

    @GetMapping("/users/{id}/albums")
    public List<AlbumEntity> getAlbums(@PathVariable String id);


}

@Component
class albumsFallBackFactory implements FallbackFactory<AlbumsServiceClient>{

    @Override
    public AlbumsServiceClient create(Throwable cause) {
        return new AlbumsServiceClientFallBack(cause);
    }
}

class AlbumsServiceClientFallBack implements AlbumsServiceClient{

    Logger logger= LoggerFactory.getLogger(this.getClass());

    private final Throwable cause;

    public AlbumsServiceClientFallBack(Throwable cause) {
        this.cause = cause;
    }


    @Override
    public List<AlbumEntity> getAlbums(String id) {

        if(cause instanceof FeignException && ((FeignException) cause).status()==404){
            logger.info("404 error occurred when getAlbums called for userID: "
                    +id+" . Error message: "
                    +cause.getLocalizedMessage());
        }else {
            logger.info("Other error took place: "+cause.getLocalizedMessage());
        }

        return new ArrayList<>();
    }
}