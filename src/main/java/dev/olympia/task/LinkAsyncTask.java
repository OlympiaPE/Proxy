package dev.olympia.task;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.olympia.session.PlayerSession;
import dev.olympia.session.SessionManager;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import dev.waterdog.waterdogpe.scheduler.Task;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class LinkAsyncTask extends Task {
    protected String key;
    protected String username;
    protected String code;
    protected String rank;

    public LinkAsyncTask(String key, String username, String code, String rank) {
        this.key = key;
        this.username = username;
        this.code = code;
        this.rank = rank;
    }

    @Override
    public void onRun(int i) {
        String urlStr = String.format("http://87.98.220.248:3001/api/link/testCode/?key=%s&username=%s&code=%s&rank=%s", key, username, code, rank);
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            int statusCode = connection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder responseStr = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseStr.append(line);
                }
                reader.close();
                JsonObject response = JsonParser.parseString(responseStr.toString()).getAsJsonObject();
                handleResponse(statusCode, response);
            } else {
                handleResponse(statusCode, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ProxyServer.getInstance().getScheduler().scheduleTask(() -> {
                ProxiedPlayer player = ProxyServer.getInstance().getPlayer(this.username);
                if (player != null) {
                    player.sendMessage("§6» §fErreur lors de la liaison de votre compte. Veuillez réessayer plus tard ou contacter un membre de l'équipe du staff.");
                }
            }, false);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    @Override
    public void onCancel() {
    }

    protected void handleResponse(int statusCode, JsonObject response) {
        ProxyServer.getInstance().getScheduler().scheduleTask(() -> {
            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(this.username);
            if (player == null) return;

            switch (statusCode) {
                case 404 -> player.sendMessage("§6» §fPour relier votre compte discord vous devez d'abord éxécuter la commande §6/link " + username + " §fsur le discord. §6discord.gg/olympiape");
                case 409 -> player.sendMessage("§6» §fCe compte est déjà relié à un compte discord.");
                case 429 -> player.sendMessage("§6» §fVous avez atteint votre nombre d'essais maximum. Veuillez réexécuter la commande §6/link " + username + " §fsur le discord. §6discord.gg/olympiape");
                case 400 -> {
                    int remainingTests = response != null ? response.get("remainingTests").getAsInt() : 0;
                    player.sendMessage("§6» §fCode incorrecte, il vous reste " + remainingTests + " essai(s).");
                }
                case 408 -> player.sendMessage("§6» §fLe délai maximum a été atteint. Veuillez réexécuter la commande §6/link " + username + " §fsur le discord. §6discord.gg/olympiape");
                case 202 -> player.sendMessage("§6» §fCompte relié avec succès !");
                default -> player.sendMessage("§6» §fErreur, veuillez réessayer plus tard ou contacter un membre de l'équipe du staff.");
            }
        }, false);
    }
}
