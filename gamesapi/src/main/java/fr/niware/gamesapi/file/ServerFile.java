package fr.niware.gamesapi.file;

public class ServerFile {

    private final int needToStart;
    private final String host;
    private final String name;
    private final String username;
    private final String password;
    private final int port;
    private final int wrldStartAward;

    public ServerFile(int needToStart, int wrldStartAward, String host, String name, String username, String password, int port) {
        this.needToStart = needToStart;
        this.wrldStartAward = wrldStartAward;
        this.host = host;
        this.name = name;
        this.username = username;
        this.password = password;
        this.port = port;
    }

    public int getNeedToStart() {
        return this.needToStart;
    }

    public int getWrldStartAward() {
        return this.wrldStartAward;
    }

    public String getHost() {
        return this.host;
    }

    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public int getPort() {
        return this.port;
    }
}
