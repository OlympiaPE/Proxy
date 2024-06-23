package dev.olympia.task;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.olympia.Loader;
import dev.olympia.utils.constants.GlobalConstants;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import dev.waterdog.waterdogpe.scheduler.Task;
import dev.waterdog.waterdogpe.utils.config.JsonConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class VoteAsyncTask extends Task {
    protected String key;
    protected String username;

    public VoteAsyncTask(String key, String username) {
        this.key = key;
        this.username = username;
    }

    @Override
    public void onRun(int i) {
        String urlStr = String.format("https://minecraftpocket-servers.com/api/?object=votes&element=claim&key=%s&username=%s", key, username.replaceAll("\\s", "_"));
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
                String response = responseStr.toString();
                if (response.equals("1")) {
                    String claimUrlStr = String.format("https://minecraftpocket-servers.com/api/?action=post&object=votes&element=claim&key=%s&username=%s", key, username.replaceAll("\\s", "_"));
                    HttpURLConnection claimConnection = null;
                    try {
                        URL claimUrl = new URL(claimUrlStr);
                        claimConnection = (HttpURLConnection) claimUrl.openConnection();
                        claimConnection.setRequestMethod("GET");
                        claimConnection.setRequestProperty("Accept", "application/json");
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (claimConnection != null) {
                            claimConnection.disconnect();
                        }
                    }
                }
                handleResponse(response);
            } else {
                handleResponse("0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ProxyServer.getInstance().getScheduler().scheduleTask(() -> {
                ProxiedPlayer player = ProxyServer.getInstance().getPlayer(this.username);
                if (player != null) {
                    player.sendMessage(GlobalConstants.PREFIX + "§cErreur lors de la liaison de votre compte. Veuillez réessayer plus tard ou contacter un membre de l'équipe du staff.");
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

    protected void handleResponse(String code) {
        ProxyServer.getInstance().getScheduler().scheduleTask(() -> {
            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(this.username);
            if (player == null) return;

            switch (code) {
                case "0" ->
                        player.sendMessage(GlobalConstants.PREFIX + "§fIl semble que vous n'ayez pas encore voté pour le serveur. Pour apporter votre soutien, veuillez visiter §eolympiape.fr/vote§c et voter sur le site proposé !");
                case "1" -> {
                    player.sendMessage(GlobalConstants.PREFIX + "§aVous avez bien voté pour le serveur.");
                    JsonConfig data = Loader.getInstance().getVoteData();
                    for (ServerInfo serverInfo : ProxyServer.getInstance().getServers()) {
                        data.setBoolean(player.getName().toLowerCase() + "." + serverInfo.getServerName().toLowerCase(), true);
                    }
                }
                default -> player.sendMessage(GlobalConstants.PREFIX + "§cVous avez déjà voté pour le serveur !");
            }
        }, false);
    }
}
