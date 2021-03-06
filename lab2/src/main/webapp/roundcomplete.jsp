<?xml version="1.0" encoding="UTF-8"?>
<%@ page import="at.ac.tuwien.big.we14.lab2.api.domain.Game" %>
<%@ page import="at.ac.tuwien.big.we14.lab2.api.domain.AnswerStatus" %>
<%@ page import="at.ac.tuwien.big.we14.lab2.api.domain.RoundStatus" %>
<% Game game = (Game) session.getAttribute("game"); %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Business Informatics Group Quiz - Zwischenstand</title>
        <link rel="stylesheet" type="text/css" href="style/screen.css" />
        <script src="js/jquery.js" type="text/javascript"></script>
        <script src="js/framework.js" type="text/javascript"></script>
    </head>
    <body id="winnerpage">
        <a class="accessibility" href="#roundwinner">Zur Rundenwertung springen</a>
        <header role="banner" aria-labelledby="mainheading"><h1 id="mainheading"><span class="accessibility">Business Informatics Group</span> Quiz</h1></header>
        <nav role="navigation" aria-labelledby="navheading">
            <h2 id="navheading" class="accessibility">Navigation</h2>
            <ul>
                <li><a id="logoutlink" title="Klicke hier um dich abzumelden" href="#" accesskey="l">Abmelden</a></li>
            </ul>
        </nav>
        
        <section role="main">
            <!-- winner message -->
            <section id="roundwinner" aria-labelledby="roundwinnerheading">
                <h2 id="roundwinnerheading" class="accessibility">Rundenzwischenstand</h2>
                <p class="roundwinnermessage">
                <% if(game.getLastRound().getRoundStatus() == RoundStatus.closed_player1Won) { %>
                	<%= game.getPlayer1Name() %> gewinnt Runde <%= game.getRounds().indexOf(game.getLastRound())+1 %>
                <% } if(game.getLastRound().getRoundStatus() == RoundStatus.closed_player2Won) { %>
                	<%= game.getPlayer2Name() %> gewinnt Runde <%= game.getRounds().indexOf(game.getLastRound())+1 %>
                <% } if(game.getLastRound().getRoundStatus() == RoundStatus.closed_tie) { %>
                	Runde <%= game.getRounds().indexOf(game.getLastRound())+1 %> geht unentschieden aus
                <% } %>			
                !</p>
            </section>
        
            <!-- round info -->    
            <section id="roundinfo" aria-labelledby="roundinfoheading">
                <h2 id="roundinfoheading" class="accessibility">Spielerinformationen</h2>
                <div id="player1info" class="playerinfo">
                    <span id="player1name" class="playername"><%= game.getPlayer1Name() %></span>
                    <ul class="playerroundsummary">
                        <% for(int i = 0; i < game.getLastRound().getAnswers().size(); i++) { %>
                        	<li><span class="accessibility">Frage <%= i+1 %>:</span><span id="player1answer<%= i %>"
                        	<% if(game.getLastRound().getAnswers().get(i).getPlayer1AnswerStatus() == AnswerStatus.answered_correct) { %>
                        		class="correct">Richtig	
                        	<% } if(game.getLastRound().getAnswers().get(i).getPlayer1AnswerStatus() == AnswerStatus.answered_failed) { %>
                        		class="incorrect">Falsch
                        	<% } if(game.getLastRound().getAnswers().get(i).getPlayer1AnswerStatus() == AnswerStatus.open) { %>
                        		class="unknown">Unbekannt
                        	<% } %>	
                        	</span></li>
                        <% } %>
                    </ul>
                    <p id="player1roundcounter" class="playerroundcounter">Gewonnene Runden: <span id="player1wonrounds" class="playerwonrounds"><%= game.getPlayer1WonRounds() %></span></p>
                </div>
                <div id="player2info" class="playerinfo">
                    <span id="player2name" class="playername"><%= game.getPlayer2Name() %></span>
                    <ul class="playerroundsummary">
                        <% for(int i = 0; i < game.getLastRound().getAnswers().size(); i++) { %>
                        	<li><span class="accessibility">Frage <%= i+1 %>:</span><span id="player2answer<%= i %>"
                        	<% if(game.getLastRound().getAnswers().get(i).getPlayer2AnswerStatus() == AnswerStatus.answered_correct) { %>
                        		class="correct">Richtig	
                        	<% } if(game.getLastRound().getAnswers().get(i).getPlayer2AnswerStatus() == AnswerStatus.answered_failed) { %>
                        		class="incorrect">Falsch
                        	<% } if(game.getLastRound().getAnswers().get(i).getPlayer2AnswerStatus() == AnswerStatus.open) { %>
                        		class="unknown">Unbekannt
                        	<% } %>	
                        	</span></li>
                        <% } %>
                    </ul>
                    <p id="player2roundcounter" class="playerroundcounter">Gewonnene Runden: <span id="player2wonrounds" class="playerwonrounds"><%= game.getPlayer2WonRounds() %></span></p>
                </div>
                <form id="form_action" method="post">
                    <% if(game.getRounds().indexOf(game.getLastRound())+1 == 5){ %>
                        <input id="action" name="action" type="hidden" value="action_gotoFinish" />
                    <% }else{ %>
                        <input id="action" name="action" type="hidden" value="action_nextQuestionRoundComplete" />
                    <% } %>
                    <a id="next" href="#" onclick="$('#form_action').submit();">Weiter</a>
                </form>
            </section>
        </section>

        <!-- footer -->
        <footer role="contentinfo">© 2014 BIG Quiz</footer>
    </body>
</html>
