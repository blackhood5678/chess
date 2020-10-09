package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public abstract class Tile {

    protected final int tileCoordinate;

    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE =  createAllPossibleEmptyTiles();

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap= new HashMap<>();

        for (int i = 0; i<BoardUtils.NUM_TILES;i++){
            emptyTileMap.put(i, new EmptyTile(i));
        }

        return ImmutableMap.copyOf(emptyTileMap);
    }

    public static Tile createTile(final int tileCoordinate, final Piece piece){
        return piece !=null ? new OccupiedTile(tileCoordinate,piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
    }

    private Tile(final int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isTileEmpty();

    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile{
        private EmptyTile(final int coordinate){
            super(coordinate);
        }

        @Override
        public boolean isTileEmpty() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }

    public static final class OccupiedTile extends Tile{
        private final Piece pieceOnTile;

       private OccupiedTile(int coordinate, final Piece pieceOnTile){
            super(coordinate);
            this.pieceOnTile=pieceOnTile;
        }

        @Override
        public boolean isTileEmpty() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return pieceOnTile;
        }

    }
}
