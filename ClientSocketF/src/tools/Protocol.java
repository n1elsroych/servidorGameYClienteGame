package tools;

public class Protocol {
    
    private static String getEspecificData(String type, String data){
        int i = data.indexOf(type) + type.length();
        int f = data.indexOf(";", i);
        return data.substring(i, f);
    }
    
    public static String parseLoginData(int sessionId, String username, String password){
        return "<login><session-id>"+sessionId+";<username>"+username+";<password>"+password+";";
    }
    
    public static String parseRegisterData(int sessionId, String username, String password){
        return "<register><session-id>"+sessionId+";<username>"+username+";<password>"+password+";";
    }
    
    public static boolean isSessionId(String data){
        return data.contains("<session-id>");
    }
    
    public static int getSessionId(String data){
        return Integer.parseInt(getEspecificData("<session-id>", data));
    }
    
    public static boolean isLoginSuccess(String data){
        return data.equals("<login-success>");
    }
    
    public static boolean isLoginError(String data){
        return data.contains("<login-error>");
    }
    
    public static String getLoginError(String data){
        return getEspecificData("<login-error>", data);
    }
    
    public static boolean isRegisterSuccess(String data){
        return data.equals("<register-success>");
    }
    
    public static boolean isRegisterError(String data){
        return data.contains("<register-error>");
    }
    
    public static String getRegisterError(String data){
        return getEspecificData("<register-error>", data);
    }
}
