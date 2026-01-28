package com.example.bazarxplatform.Mapper;

import com.example.bazarxplatform.Dto.Response.FavoriteResponse;
import com.example.bazarxplatform.Entity.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FavoriteMapper {

    @Autowired
    private AdMapper adMapper;

    // Entity -> Response
    public FavoriteResponse toResponse(Favorite favorite) {
        if (favorite == null) {
            return null;
        }

        FavoriteResponse response = new FavoriteResponse();
        response.setId(favorite.getId());
        response.setUserId(favorite.getUser().getId());

        // Convert ad to response (without images for simplicity, can be added later)
        response.setAd(adMapper.toAdResponse(favorite.getAd(), null));

        response.setAddedAt(favorite.getAddedAt());

        return response;
    }
}