//	Download: http://tictactoe.ai.dhavalkadia.com

package com.dhavalkadia.tictactoe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TicTacToe ttt = new TicTacToe();
    int playerMove, moveCount = 0, algoMove;

    ImageButton button1;
    ImageButton button2;
    ImageButton button3;
    ImageButton button4;
    ImageButton button5;
    ImageButton button6;
    ImageButton button7;
    ImageButton button8;
    ImageButton button9;

    TextView message;

    FloatingActionButton refresh;

    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        button1=(ImageButton)findViewById(R.id.Button1);
        button2=(ImageButton)findViewById(R.id.Button2);
        button3=(ImageButton)findViewById(R.id.Button3);
        button4=(ImageButton)findViewById(R.id.Button4);
        button5=(ImageButton)findViewById(R.id.Button5);
        button6=(ImageButton)findViewById(R.id.Button6);
        button7=(ImageButton)findViewById(R.id.Button7);
        button8=(ImageButton)findViewById(R.id.Button8);
        button9=(ImageButton)findViewById(R.id.Button9);

        message = (TextView)findViewById(R.id.Message);

        refresh = (FloatingActionButton)findViewById(R.id.Refresh);
        refresh.setVisibility(View.INVISIBLE);

        animation = new RotateAnimation(360.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setRepeatCount(-1);
        animation.setDuration(2000);

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","mail@dhavalkadia.com", null));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Your Tic Tac Toe App");
                intent.putExtra(Intent.EXTRA_TEXT, "Dhaval, I would like to say that ");
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//         Handle action bar item clicks here. The action bar will
//         automatically handle clicks on the Home/Up button, so long
//         as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.privacy) {
            Intent intent = new Intent(this, YourPrivacy.class);
            startActivity(intent);
        } else if (id == R.id.subscribe) {
            Intent intent = new Intent(this, Subscribe.class);
            startActivity(intent);
        } else if (id == R.id.share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,"http://play.dhavalkadia.com");
            sendIntent.setType("text/plain");
            Intent.createChooser(sendIntent,"Share via");
            startActivity(sendIntent);
        } else if (id == R.id.app) {
            Intent intent = new Intent(this, App.class);
            startActivity(intent);
        } else if (id == R.id.website) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://dhavalkadia.com"));
            startActivity(i);
        } else if (id == R.id.about) {
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void refreshGame(View view)
    {
        message.setText("Let's play once again!");
        //#1System.out.println("Refresh Clicked");
//        refresh.startAnimation(animation);
        replay();
        refresh.setVisibility(View.INVISIBLE);

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                replay();
//                refresh.setVisibility(View.INVISIBLE);
//            }
//        }, 500);

//        System.out.println("Refresh Clicked");
//        refresh.startAnimation(animation);
    }

    public void onClick(View view) {

        moveCount++;

        switch(view.getId())
        {
            case R.id.Button1:
                //#1System.out.println("Button 1");
                playerMove = 1;
                button1.setImageResource(R.drawable.ic_clear_black_24dp);
                button1.setEnabled(false);
                break;
            case R.id.Button2:
                //#1System.out.println("Button 2");
                playerMove = 2;
                button2.setImageResource(R.drawable.ic_clear_black_24dp);
                button2.setEnabled(false);
                break;
            case R.id.Button3:
                //#1System.out.println("Button 3");
                playerMove = 3;
                button3.setImageResource(R.drawable.ic_clear_black_24dp);
                button3.setEnabled(false);
                break;
            case R.id.Button4:
                //#1System.out.println("Button 4");
                playerMove = 4;
                button4.setImageResource(R.drawable.ic_clear_black_24dp);
                button4.setEnabled(false);
                break;
            case R.id.Button5:
                //#1System.out.println("Button 5");
                playerMove = 5;
                button5.setImageResource(R.drawable.ic_clear_black_24dp);
                button5.setEnabled(false);
                break;
            case R.id.Button6:
                //#1System.out.println("Button 6");
                playerMove = 6;
                button6.setImageResource(R.drawable.ic_clear_black_24dp);
                button6.setEnabled(false);
                break;
            case R.id.Button7:
                //#1System.out.println("Button 7");
                playerMove = 7;
                button7.setImageResource(R.drawable.ic_clear_black_24dp);
                button7.setEnabled(false);
                break;
            case R.id.Button8:
                //#1System.out.println("Button 8");
                playerMove = 8;
                button8.setImageResource(R.drawable.ic_clear_black_24dp);
                button8.setEnabled(false);
                break;
            case R.id.Button9:
                //#1System.out.println("Button 9");
                playerMove = 9;
                button9.setImageResource(R.drawable.ic_clear_black_24dp);
                button9.setEnabled(false);
                break;
            default:
                throw new RuntimeException("Unknow button ID");
        }

        algoMove = ttt.play(playerMove, moveCount);

        //#1System.out.println("algoMove = "+algoMove);

        if(algoMove > 10)
        {
            button1.setEnabled(false);
            button2.setEnabled(false);
            button3.setEnabled(false);
            button4.setEnabled(false);
            button5.setEnabled(false);
            button6.setEnabled(false);
            button7.setEnabled(false);
            button8.setEnabled(false);
            button9.setEnabled(false);

            if (algoMove > 10 && algoMove < 20)
            {
                message.setText("AI Won!");
                algoMove = algoMove % 10;
            }
            else if(algoMove == 20)
            {
                message.setText("Tie!");
            }
            else if(algoMove == 21)
            {
                message.setText("You Won!");
            }

            refresh.setVisibility(View.VISIBLE);
        }

        if(algoMove > 0 && algoMove < 10)
        switch(algoMove)
        {
            case 1:
                button1.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp);
                button1.setEnabled(false);
                break;
            case 2:
                button2.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp);
                button2.setEnabled(false);
                break;
            case 3:
                button3.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp);
                button3.setEnabled(false);
                break;
            case 4:
                button4.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp);
                button4.setEnabled(false);
                break;
            case 5:
                button5.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp);
                button5.setEnabled(false);
                break;
            case 6:
                button6.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp);
                button6.setEnabled(false);
                break;
            case 7:
                button7.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp);
                button7.setEnabled(false);
                break;
            case 8:
                button8.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp);
                button8.setEnabled(false);
                break;
            case 9:
                button9.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp);
                button9.setEnabled(false);
                break;
            case 100:
                System.out.println("Critical Problem");
                break;
            case 101:
                System.out.println("Critical Problem");
                break;
            default:
                throw new RuntimeException("Unknow button ID");
        }
    }

    private void replay() {

        button1.setImageResource(android.R.color.transparent);
        button2.setImageResource(android.R.color.transparent);
        button3.setImageResource(android.R.color.transparent);
        button4.setImageResource(android.R.color.transparent);
        button5.setImageResource(android.R.color.transparent);
        button6.setImageResource(android.R.color.transparent);
        button7.setImageResource(android.R.color.transparent);
        button8.setImageResource(android.R.color.transparent);
        button9.setImageResource(android.R.color.transparent);

        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        button5.setEnabled(true);
        button6.setEnabled(true);
        button7.setEnabled(true);
        button8.setEnabled(true);
        button9.setEnabled(true);

        moveCount = 0;

        ttt.refresh();
    }
}