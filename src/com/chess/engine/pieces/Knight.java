package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.AttackingMove;

public class Knight extends Piece{
    private final static int[] CANDIDATE_MOVE_COORDINATES ={-17, -15, -10, -6, 6, 10, 15, 17};

    public Knight(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
       int candidateDestinationCoordinates;
       final  List<Move> legalMoves=new ArrayList<>();
        for (final int currentCandidateOffset: CANDIDATE_MOVE_COORDINATES){
            candidateDestinationCoordinates=currentCandidateOffset+this.piecePosition;
            if (BoardUtils.isValidCoordinate(candidateDestinationCoordinates)){
                if (    isFirstColumnExclusion(this.piecePosition, currentCandidateOffset)||
                        isSecondColumnExclusion(this.piecePosition,currentCandidateOffset) ||
                        isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isEightColumnExclusion(this.piecePosition, currentCandidateOffset)){
                     continue;
                }
                final Tile candidateDestinationTile=board.getTile(candidateDestinationCoordinates);
                if (!candidateDestinationTile.isTileEmpty()){
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinates));
                }else{
                    final Piece pieceDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = candidateDestinationTile.getPiece().pieceAlliance;
                    if (this.pieceAlliance != pieceAlliance){
                        legalMoves.add(new AttackingMove(board, this, candidateDestinationCoordinates, pieceDestination));
                    }
                }
            }
        }
       return ImmutableList.copyOf(legalMoves);
    }
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset==-10
                || candidateOffset==6 ||candidateOffset==15);
    }
    private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.SECOUND_COLUMN[currentPosition] && (candidateOffset == -10 || candidateOffset==6);
    }
    private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == -6 || candidateOffset==10
                || candidateOffset==6 ||candidateOffset==15);
    }
    private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset == -15 || candidateOffset==-6
                || candidateOffset==10 ||candidateOffset==17);
    }
}
