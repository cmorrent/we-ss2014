<?xml version="1.0" encoding="UTF-8"?>
<%@ page import="at.ac.tuwien.big.we14.lab2.api.domain.Game" %>
<%@ page import="at.ac.tuwien.big.we14.lab2.api.domain.GameStatus" %>
<% Game game = (Game) session.getAttribute("game"); %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Business Informatics Group Quiz - Spielende</title>
        <link rel="stylesheet" type="text/css" href="style/screen.css" />
        <script src="js/jquery.js" type="text/javascript"></script>
        <script src="js/framework.js" type="text/javascript"></script>
    </head>
    <body id="winnerpage">
        <a class="accessibility" href="#roundwinner">Zur Spielwertung springen</a>
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
                <h2 id="roundwinnerheading" class="accessibility">Endstand</h2>
                <p class="roundwinnermessage">
                <% if(game.getGameStatus() == GameStatus.closed_player1Won) { %>
                	<%= game.getPlayer1Name() %> gewinnt
                <% } if(game.getGameStatus() == GameStatus.closed_player2Won) { %>
                	<%= game.getPlayer2Name() %> gewinnt
                <% } if(game.getGameStatus() == GameStatus.closed_tie) { %>
                	Spiel geht unentschieden aus
                <% } %>
                !</p>
            </section>
        
            <!-- round info -->    
            <section id="roundinfo" aria-labelledby="roundinfoheading">
                <h2 id="roundinfoheading" class="accessibility">Spielerinformationen</h2>
                <div id="player1info" class="playerinfo">
                    <span id="player1name" class="playername"><%= game.getPlayer1Name() %></span>
                    <p id="player1roundcounter" class="playerroundcounter">Gewonnene Runden: <span id="player1wonrounds" class="playerwonrounds"><%= game.getPlayer1WonRounds() %></span></p>
                </div>
                <div id="player2info" class="playerinfo">
                    <span id="player2name" class="playername"><%= game.getPlayer2Name() %></span>
                    <p id="player2roundcounter" class="playerroundcounter">Gewonnene Runden: <span id="player2wonrounds" class="playerwonrounds"><%= game.getPlayer2WonRounds() %></span></p>
                </div>
                <form id="form_action" method="post">
                    <input id="action" name="action" type="hidden" value="action_restart" />
                    <a id="next" href="#" onclick="$('#form_action').submit();">Neues Spiel</a>
                </form>

            </section>
        </section>

        <!-- footer -->
        <footer role="contentinfo">© 2014 BIG Quiz</footer>
        
        <script type="text/javascript">
            //<![CDATA[
            
            $(document).ready(function() {
            	if(supportsLocalStorage()) {
                    var date = new Date();
                    var day = date.getDate();
                    var month = date.getMonth()+1;
                    var year = date.getFullYear();
                    var hour = date.getHours();
                    var min = date.getMinutes();
                    var sec = date.getSeconds();
                    var timestamp = day+"."+month+"."+year+", "+hour+":"+min+":"+sec;
                    $("#timestamp").text(timestamp);
                    localStorage.lastgameTimestamp = timestamp;
                }
            });
            
            //]]>
        </script>
    </body>
</html>
