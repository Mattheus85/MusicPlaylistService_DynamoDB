package com.amazon.ata.music.playlist.service.activity;

import com.amazon.ata.music.playlist.service.dynamodb.AlbumTrackDao;
import com.amazon.ata.music.playlist.service.dynamodb.PlaylistDao;
import com.amazon.ata.music.playlist.service.dynamodb.models.Playlist;
import com.amazon.ata.music.playlist.service.exceptions.InvalidAttributeValueException;
import com.amazon.ata.music.playlist.service.helpers.PlaylistTestHelper;
import com.amazon.ata.music.playlist.service.models.requests.CreatePlaylistRequest;
import com.amazon.ata.music.playlist.service.models.results.CreatePlaylistResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreatePlaylistActivityTest {
    @Mock
    private PlaylistDao playlistDao;

    @Mock
    private AlbumTrackDao albumTrackDao;

    private CreatePlaylistActivity createPlaylistActivity;

    @BeforeEach
    private void setup() {
        initMocks(this);
        createPlaylistActivity = new CreatePlaylistActivity(playlistDao);
    }

    @Test
    void handleRequest_validRequest_createsPlaylist() {
        // GIVEN
        // a valid request
        Playlist playlist = PlaylistTestHelper.generatePlaylistWithNAlbumTracks(0);
        String name = playlist.getName();
        String customerId = playlist.getCustomerId();
        List<String> tagList = new ArrayList<>();
        tagList.add("tag1");
        tagList.add("tag2");
        CreatePlaylistRequest createPlaylistRequest = CreatePlaylistRequest
                .builder()
                .withName(name)
                .withCustomerId(customerId)
                .withTags(new ArrayList<>(tagList))
                .build();

        // WHEN
        CreatePlaylistResult createPlaylistResult = createPlaylistActivity
                .handleRequest(createPlaylistRequest, null);

        // THEN
        assertEquals(name, createPlaylistResult.getPlaylist().getName());
        assertEquals(customerId, createPlaylistResult.getPlaylist().getCustomerId());
        assertEquals(0, createPlaylistResult.getPlaylist().getSongCount());
        assertEquals(tagList, createPlaylistResult.getPlaylist().getTags());
    }

    @Test
    void handleRequest_validRequestWithoutTags_createsPlaylistWithNullValueForTags() {
        // GIVEN
        // a valid request
        Playlist playlist = PlaylistTestHelper.generatePlaylistWithNAlbumTracks(0);
        String name = playlist.getName();
        String customerId = playlist.getCustomerId();
        CreatePlaylistRequest createPlaylistRequest = CreatePlaylistRequest
                .builder()
                .withName(name)
                .withCustomerId(customerId)
                .build();

        // WHEN
        CreatePlaylistResult createPlaylistResult = createPlaylistActivity
                .handleRequest(createPlaylistRequest, null);

        // THEN
        assertEquals(name, createPlaylistResult.getPlaylist().getName());
        assertEquals(customerId, createPlaylistResult.getPlaylist().getCustomerId());
        assertEquals(0, createPlaylistResult.getPlaylist().getSongCount());
        assertNull(createPlaylistResult.getPlaylist().getTags());
    }

    @Test
    void handleRequest_invalidName_throwsInvalidAttributeValueException() {
        // GIVEN
        // a request with an invalid name
        Playlist playlist = PlaylistTestHelper.generatePlaylistWithNAlbumTracks(0);
        String name = "";
        String customerId = playlist.getCustomerId();
        List<String> tagList = new ArrayList<>();
        tagList.add("tag1");
        tagList.add("tag2");
        CreatePlaylistRequest createPlaylistRequest = CreatePlaylistRequest
                .builder()
                .withName(name)
                .withCustomerId(customerId)
                .withTags(new ArrayList<>(tagList))
                .build();

        // WHEN & THEN
        assertThrows(InvalidAttributeValueException.class, () -> createPlaylistActivity
                .handleRequest(createPlaylistRequest, null));
    }

    @Test
    void handleRequest_invalidCustomerId_throwsInvalidAttributeValueException() {
        // GIVEN
        // a request with an invalid name
        Playlist playlist = PlaylistTestHelper.generatePlaylistWithNAlbumTracks(0);
        String name = playlist.getName();
        String customerId = "";
        List<String> tagList = new ArrayList<>();
        tagList.add("tag1");
        tagList.add("tag2");
        CreatePlaylistRequest createPlaylistRequest = CreatePlaylistRequest
                .builder()
                .withName(name)
                .withCustomerId(customerId)
                .withTags(new ArrayList<>(tagList))
                .build();

        // WHEN & THEN
        assertThrows(InvalidAttributeValueException.class, () -> createPlaylistActivity
                .handleRequest(createPlaylistRequest, null));
    }
}
