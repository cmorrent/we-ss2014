<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Business Informatics Group Quiz</title>
        <link rel="stylesheet" type="text/css" href="style/screen.css" />
        <script src="js/jquery.js" type="text/javascript"></script>
        <script src="js/framework.js" type="text/javascript"></script>
    </head>
    <body id="questionpage">
        <a class="accessibility" href="#question">Zur Frage springen</a>
        <header role="banner" aria-labelledby="mainheading"><h1 id="mainheading"><span class="accessibility">Business Informatics Group</span> Quiz</h1></header>
        <nav role="navigation" aria-labelledby="navheading">
            <h2 id="navheading" class="accessibility">Navigation</h2>
            <ul>
                <li><a id="logoutlink" title="Klicke hier um dich abzumelden" href="#" accesskey="l">Abmelden</a></li>
            </ul>
        </nav>
        
        <!-- round info -->
        <section role="main">
            <section id="roundinfo" aria-labelledby="roundinfoheading">
                <h2 id="roundinfoheading" class="accessibility">Spielerinformationen</h2>
                <div id="player1info">
                    <span id="player1name">Spieler 1</span>
                    <ul class="playerroundsummary">
                        <li><span class="accessibility">Frage 1:</span><span id="player1answer1" class="correct">Richtig</span></li>
                        <li><span class="accessibility">Frage 2:</span><span id="player1answer2" class="incorrect">Falsch</span></li>
                        <li><span class="accessibility">Frage 3:</span><span id="player1answer3" class="unknown">Unbekannt</span></li>
                    </ul>
                </div>
                <div id="player2info">
                    <span id="player2name">Spieler 2</span>
                    <ul class="playerroundsummary">
                        <li><span class="accessibility">Frage 1:</span><span id="player2answer1" class="correct">Richtig</span></li>
                        <li><span class="accessibility">Frage 2:</span><span id="player2answer2" class="correct">Richtig</span></li>
                        <li><span class="accessibility">Frage 3:</span><span id="player2answer3" class="unknown">Unbekannt</span></li>
                    </ul>
                </div>
                <div id="currentcategory"><span class="accessibility">Kategorie:</span> Sport</div>
            </section>
            
            <!-- Question -->
            <section id="question" aria-labelledby="questionheading">
                
                <form id="questionform" method="post">
                    <h2 id="questionheading" class="accessibility">Frage</h2>
                    <p id="questiontext">Welche zwei LVAs werden im Model EWA zusammengefasst?</p>
                    <ul id="answers">
                        <li><input id="option1" name="option1" type="checkbox" value="1"/><label for="option1">IT Strategie</label></li>
                        <li><input id="option2" name="option2" type="checkbox" value="2"/><label for="option2">Web Engineering</label></li>
                        <li><input id="option3" name="option3" type="checkbox" value="3"/><label for="option3">Semistrukturierte Daten</label></li>
                        <li><input id="option4" name="option4" type="checkbox" value="4"/><label for="option4">Objektorientierte Modellierung</label></li>
                    </ul>
                    <input id="answer_count" name="answer_count" value="4" type="hidden"/>
                    <input id="action" name="action" type="hidden" value="action_question" />
                    <input id="timeleftvalue" type="hidden" value="100"/>
                    <input id="question_id" name="question_id" type="hidden" value="2"/>
                    <input id="next" type="submit" value="weiter" accesskey="s"/>
                </form>
            </section>
            
            <section id="timer" aria-labelledby="timerheading">
                <h2 id="timerheading" class="accessibility">Timer</h2>
                <p><span id="timeleftlabel">Verbleibende Zeit:</span> <time id="timeleft">00:30</time></p>
                <meter id="timermeter" min="0" low="20" value="100" max="100"/>
            </section>
            
            <section id="lastgame">
                <p>Letztes Spiel: Nie</p>
            </section>
        </section>

        <!-- footer -->
        <footer role="contentinfo">© 2014 BIG Quiz</footer>
        
        <script type="text/javascript">
            //<![CDATA[
            
            // initialize time
            $(document).ready(function() {
                var maxtime = 30;
                var hiddenInput = $("#timeleftvalue");
                var meter = $("#timer meter");
                var timeleft = $("#timeleft");
                
                hiddenInput.val(maxtime);
                meter.val(maxtime);
                meter.attr('max', maxtime);
                meter.attr('low', maxtime/100*20);
                timeleft.text(secToMMSS(maxtime));
            });
            
            // update time
            function timeStep() {
                var hiddenInput = $("#timeleftvalue");
                var meter = $("#timer meter");
                var timeleft = $("#timeleft");
                
                var value = $("#timeleftvalue").val();
                if(value > 0) {
                    value = value - 1;   
                }
                
                hiddenInput.val(value);
                meter.val(value);
                timeleft.text(secToMMSS(value));
                
                if(value <= 0) {
                    $('#questionform').submit();
                }
            }
            
            window.setInterval(timeStep, 1000);
            
            //]]>
        </script>
    </body>
</html>
