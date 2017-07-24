package com.droidrank.tictactoe;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button[] block = new Button[GameState.NUM_SQUARES];
    private Button restart;
    private TextView result;
    private GameState state;
    private OnClickListener blockListener;
    private boolean gameInProgress = false;


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        state = new GameState();

        blockListener = new OnClickListener() {
            @Override public void onClick( View v ) {
                onBlockClick( v );
            }
        };

        block[0] = (Button) findViewById( R.id.bt_block1 );
        block[1] = (Button) findViewById( R.id.bt_block2 );
        block[2] = (Button) findViewById( R.id.bt_block3 );
        block[3] = (Button) findViewById( R.id.bt_block4 );
        block[4] = (Button) findViewById( R.id.bt_block5 );
        block[5] = (Button) findViewById( R.id.bt_block6 );
        block[6] = (Button) findViewById( R.id.bt_block7 );
        block[7] = (Button) findViewById( R.id.bt_block8 );
        block[8] = (Button) findViewById( R.id.bt_block9 );
        for ( int i = 0; i < GameState.NUM_SQUARES; i++ ) {
            block[i].setTag( R.id.block_position, i );
            block[i].setOnClickListener( blockListener );
        }

        result = (TextView) findViewById( R.id.tv_show_result );
        restart = (Button) findViewById( R.id.bt_restart_game );

        /**
         * Restarts the game
         */
        restart.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                onRestartClick();
            }
        } );

    }

    private String getPlayerSymbol( int playerId ) {
        if ( playerId == 1 ) {
            return getResources().getString( R.string.player_1_move );
        } else if ( playerId == 2 ) {
            return getResources().getString( R.string.player_2_move );
        }
        return " ";
    }

    private void onBlockClick( View v ) {
        if ( gameInProgress ) {
            Object tag = v.getTag( R.id.block_position );
            if ( tag != null && tag instanceof Integer ) {
                int id = (Integer) tag;
                if ( state.getPlayerAtSquare( id ) == 0 ) {
                    state.setSquare( id );
                    block[id].setText( getPlayerSymbol( state.getPlayerAtSquare( id ) ) );
                    checkForWin();
                }
            } else {
                throw new IllegalStateException( "Unexpected Block Clicked" ); // CRASH
            }
        }
    }

    private void checkForWin() {
        int winner = state.winningPlayer();
        switch ( winner ) {
            case 0:// tie
                onTie();
                break;
            case 1: // player 1
                onWinner( 1 );
                break;
            case 2: // player 2
                onWinner( 2 );
                break;
            default: // no-op
        }
    }

    private void onWinner( int playerId ) {
        if ( playerId == 1 ) {
            result.setText( R.string.player_1_wins );
        } else if ( playerId == 2 ) {
            result.setText( R.string.player_2_wins );
        }
        onGameEnd();
    }

    private void onTie() {
        result.setText( R.string.draw );
        onGameEnd();
    }

    private void onGameEnd() {
        gameInProgress = false;
        drawRestartButton();
    }

    private void showRestartDialog() {
        AlertDialog dialog = new AlertDialog.Builder( this ).create();
        dialog.setTitle( R.string.restart_message );
        dialog.setButton( DialogInterface.BUTTON_POSITIVE, getResources().getString( R.string.restart_button_pos ), new DialogInterface.OnClickListener() {
            @Override public void onClick( DialogInterface dialog, int which ) {
                onGameStart();
            }
        } );
        dialog.setButton( DialogInterface.BUTTON_NEGATIVE, getResources().getString( R.string.restart_button_neg ), new DialogInterface.OnClickListener() {
            @Override public void onClick( DialogInterface dialog, int which ) {
                // No-op
            }
        } );
        dialog.show();
    }

    private void onRestartClick() {
        if ( gameInProgress ) {
            showRestartDialog();
            return;
        }
        onGameStart();
    }

    private void onGameStart() {
        gameInProgress = true;
        drawRestartButton();
        state.resetBoard();
        result.setText( null );
        for ( int i = 0; i < GameState.NUM_SQUARES; i++ ) {
            block[i].setText( getPlayerSymbol( state.getPlayerAtSquare( i ) ) );
        }
    }

    private void drawRestartButton() {
        if ( gameInProgress ) {
            restart.setText( R.string.restart_button_text_in_middle_of_game );
        } else {
            restart.setText( R.string.restart_button_text_initially );
        }
    }

}
