package com.amazon.ata.music.playlist.service.converters;

import com.amazon.ata.music.playlist.service.dynamodb.models.AlbumTrack;
import com.amazon.ata.music.playlist.service.models.PlaylistModel;
import com.amazon.ata.music.playlist.service.dynamodb.models.Playlist;
import com.amazon.ata.music.playlist.service.models.SongModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ModelConverter {

    /**
     * Converts a provided {@link Playlist} into a {@link PlaylistModel} representation.
     * @param playlist the playlist to convert
     * @return the converted playlist
     */
    public PlaylistModel toPlaylistModel(Playlist playlist) {

        return PlaylistModel.builder()
            .withId(playlist.getId())
            .withCustomerId(playlist.getCustomerId())
            .withName(playlist.getName())
            .withSongCount(playlist.getSongCount())
            .withTags(playlist.getTags() != null ? new ArrayList<>(playlist.getTags()) : null)
            .build();
    }

    /**
     * Converts a provided {@link AlbumTrack} into a {@link SongModel} representation.
     * @param albumTrack the albumTrack (or song) to convert
     * @return the converted albumTrack
     */
    public SongModel toSongModel(AlbumTrack albumTrack) {

        return SongModel.builder()
                .withAlbum(albumTrack.getAlbumName())
                .withAsin(albumTrack.getAsin())
                .withTitle(albumTrack.getSongTitle())
                .withTrackNumber(albumTrack.getTrackNumber())
                .build();
    }

    /**
     * Converts a provided {@link List<AlbumTrack>} into a {@link List<SongModel>} representation.
     * @param albumTrackList the albumTrackList to be converted
     * @return the converted albumTrackList
     */
    public List<SongModel> toSongModelList(List<AlbumTrack> albumTrackList) {
        List<SongModel> songModelList = new ArrayList<>();

        for (AlbumTrack song : albumTrackList) {
            songModelList.add(toSongModel(song));
        }

        return songModelList;
    }
}
