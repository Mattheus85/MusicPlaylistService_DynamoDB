package com.amazon.ata.music.playlist.service.lambda;

import com.amazon.ata.music.playlist.service.dependency.DaggerServiceComponent;
import com.amazon.ata.music.playlist.service.dependency.ServiceComponent;
import com.amazon.ata.music.playlist.service.models.requests.CreatePlaylistRequest;
import com.amazon.ata.music.playlist.service.models.results.CreatePlaylistResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class CreatePlaylistActivityProvider implements RequestHandler<CreatePlaylistRequest, CreatePlaylistResult> {

    @Override
    public CreatePlaylistResult handleRequest(final CreatePlaylistRequest createPlaylistRequest, Context context) {
        return getDaggerServiceComponent()
                .provideCreatePlaylistActivity()
                .handleRequest(createPlaylistRequest, context);
    }

    private ServiceComponent getDaggerServiceComponent() {
        return DaggerServiceComponent.create();
    }
}
