package org.example.richlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.zkoss.zk.ui.GenericRichlet;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.RichletConfig;
import org.example.service.GameService;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.*;

import java.util.StringTokenizer;

public class GameRichlet extends GenericRichlet {

    @Autowired
    private GameService gameservice;

    private static final String playerA = "A";
    private static final String playerB = "B";
    private static final String largePit = "L";

    @Override
    public void init(RichletConfig config) {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getWebApp().getServletContext());
    }

    @Override
    public void service(Page page) throws Exception {

        Image img = new Image("~./img/mancala.png");
        img.setHeight("40px");
        img.setPage(page);

        Window window = new Window("Let's Play!", "normal", false);
        window.setPage(page);
        window.addSclass("~./css/web-globalstyles.css");

        Label lbl_tag = new Label("It's a 2 player game.");
        window.appendChild(new Separator());
        window.appendChild(lbl_tag);
        window.appendChild(new Separator());

        Label lbl_player1 = new Label("Enter name for player 1:");
        window.appendChild(new Separator());
        window.appendChild(lbl_player1);
        Textbox player1 = new Textbox();
        player1.addEventListener("onChange", event ->
            gameservice.setPlayer(playerA, player1.getValue())
        );
        window.appendChild(player1);

        Label lbl_player2 = new Label("Enter name for player 2:");
        window.appendChild(new Separator());
        window.appendChild(lbl_player2);
        Textbox player2 = new Textbox();
        player2.addEventListener("onChange", event ->
            gameservice.setPlayer(playerB, player2.getValue())
        );
        window.appendChild(player2);

        Window gamewindow = new Window("", "normal", false);
      //  gamewindow.setWidth("50%");
        gamewindow.setPosition("center");

        Button button = new Button("Start Game");
        button.addEventListener("onClick", event -> {
            if(player1.getValue().isEmpty()){
                Clients.showNotification("Enter name for Player 1");
            } else if(player2.getValue().isEmpty()){
                Clients.showNotification("Enter name for Player 2");
            }
            else{
                Label label1 = new Label("Player 1: " + gameservice.getPlayerA());
                Label label2 = new Label("Player 2: " + gameservice.getPlayerB());
                gamewindow.setPage(page);
                window.getChildren().clear();
                window.appendChild(label1);
                window.appendChild(new Space());
                window.appendChild(label2);
            }
        });

        window.appendChild(new Separator());
        window.appendChild(button);

        Button bigpit_B = createBigPit(playerB+largePit);
        gamewindow.appendChild(bigpit_B);

        for(int j=6; j>0; j--){
            Button smallpitB = createPit(playerB+j);
            gamewindow.appendChild(new Space());
            gamewindow.appendChild(smallpitB);
        }

        gamewindow.appendChild(new Separator());

        for(int i=0; i<17; i++) {
            gamewindow.appendChild(new Space());
        }

        for(int i=1; i<7; i++){
            Button smallpitA = createPit(playerA+i);
            gamewindow.appendChild(smallpitA);
            gamewindow.appendChild(new Space());
        }

        Button bigpit_A = createBigPit(playerA+largePit);
        gamewindow.appendChild(bigpit_A);

    }

    private Button createPit(String pitIndex){

        Button smallpit = new Button();
        smallpit.setId(pitIndex);
        smallpit.setImage("~./img/smallpit.png");
        smallpit.setLabel(gameservice.getPitcount(smallpit.getId()));
        smallpit.setOrient("vertical");
        smallpit.setAutodisable("self");
        smallpit.addEventListener("onClick", event -> {
            if(Integer.parseInt(gameservice.getPitcount(smallpit.getId()))==0) {
                Clients.showNotification("Pit has no stones to pick. Choose another pit");
            }
            Clients.showNotification("Picked "+gameservice.getPitcount(smallpit.getId())+" stones for sow");
            gameservice.sow(smallpit.getId());
        });

        return smallpit;
    }

    private Button createBigPit(String pitIndex){
        Button bigpit = new Button();
        bigpit.setId(pitIndex);
        bigpit.setImage("~./img/bigpit.png");
        bigpit.setOrient("vertical");
        bigpit.setLabel(gameservice.getPitcount(String.valueOf(bigpit.getId())));
        bigpit.addEventListener("onClick", event ->
            Clients.showNotification("Players cannot pick from a Big Pit")
        );

        return bigpit;
    }



}
