/**
 * COPYRIGHT 北京微梦创新科技发展有限公司 2016
 * chetu - XSSSecurityManager.java
 * 2016年9月20日
 * seven
 */
package com.ndjk.cl.filter;

/**
 * @author seven
 *
 */
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class XSSSecurityManager {
    private static Logger logger = Logger.getLogger(XSSSecurityManager.class);
    public static String REGEX;
    public static String[] REGEXS = new String[15];
    public static Pattern XSS_PATTERN;
    private static Pattern PATH_PATTERN = Pattern
            .compile("^[A-Za-z0-9_-|\\/|\\.]*$");

    public static void init() {
        logger.info("XSSSecurityManager.initConfig(String path) begin");

        REGEXS[0] = ".*[A|a][L|l][E|e][R|r][T|t]\\s*\\(.*\\).*";

        REGEXS[1] = ".*[W|w][I|i][N|n][D|d][O|o][W|w]\\.[L|l][O|o][C|c][A|a][T|t][I|i][O|o][N|n]\\s*=.*";

        REGEXS[2] = ".*[S|s][T|t][Y|y][L|l][E|e]\\s*=.*[X|x]:[E|e][X|x].*[P|p][R|r][E|e][S|s]{1,2}[I|i][O|o][N|n]\\s*\\(.*\\).*";

        REGEXS[3] = ".*[D|d][O|o][C|c][U|u][M|m][E|e][N|n][T|t]\\.[C|c][O|o]{2}[K|k][I|i][E|e].*";

        REGEXS[4] = ".*[E|e][V|v][A|a][L|l]\\s*\\(.*\\).*";

        REGEXS[5] = ".*[U|u][N|n][E|e][S|s][C|c][A|a][P|p][E|e]\\s*\\(.*\\).*";

        REGEXS[6] = ".*[E|e][X|x][E|e][C|c][S|s][C|c][R|r][I|i][P|p][T|t]\\s*\\(.*\\).*";

        REGEXS[7] = ".*[M|m][S|s][G|g][B|b][O|o][X|x]\\s*\\(.*\\).*";

        REGEXS[8] = ".*[C|c][O|o][N|n][F|f][I|i][R|r][M|m]\\s*\\(.*\\).*";

        REGEXS[9] = ".*[P|p][R|r][O|o][M|m][P|p][T|t]\\s*\\(.*\\).*";

        REGEXS[10] = ".*<[S|s][C|c][R|r][I|i][P|p][T|t]>.*</[S|s][C|c][R|r][I|i][P|p][T|t]>.*";

        REGEXS[11] = "[.&[^\"]]*\"[.&[^\"]]*";

        REGEXS[12] = "[.&[^']]*'[.&[^']]*";

        REGEXS[13] = ".&[^a]]|[|a|\n|\r\n|\r|| | ]]*<[S|s][C|c][R|r][I|i][P|p][T|t]>.*</[S|s][C|c][R|r][I|i][P|p][T|t]>[[.&[^a]]|[|a|\n|\r\n|\r|| | ]]*";

        REGEXS[14] = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(.*\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b.*)";

        StringBuffer tempStr = new StringBuffer("^");
        for (String tmp : REGEXS) {
            tmp = tmp.replaceAll("\\\\\\\\", "\\\\");
            tempStr.append(tmp);
            tempStr.append("|");
        }
        if (tempStr.charAt(tempStr.length() - 1) == '|') {
            REGEX = tempStr.substring(0, tempStr.length() - 1) + "$";
            logger.info("安全匹配规则" + REGEX);
        } else {
            logger.error("安全过滤配置文件加载失败:正则表达式异常 " + tempStr.toString());
        }

        XSS_PATTERN = Pattern.compile(REGEX);

        logger.info("XSSSecurityManager.initConfig(String path) end");
    }

    public static String securityReplace(String text) {
        if (isNullStr(text)) {
            return text;
        }
        return text.replaceAll(REGEX, XSSSecurityConfig.REPLACEMENT);
    }

    public static boolean matches(String text) {
        if (text == null) {
            return false;
        }
        return XSS_PATTERN.matcher(text).matches();
    }

    public static boolean isNullStr(String value) {
        return (value == null) || (value.trim().equals(""));
    }

    public static boolean checkPageLink(String path) {
        if (path == null) {
            return false;
        }
        return PATH_PATTERN.matcher(path).matches();
    }

    public static String getBasePath(HttpServletRequest request) {
        String path = request.getContextPath();
        return request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + path + "/";
    }

    public static void main(String[] args) {
        try {
            try {
                System.out
                        .println(matches("%3Cscript+%3E%5Bwindow%5B%27location%27%5D%3D%27%5Cx6a%5Cx61%5Cx76%5Cx61%5Cx73%5Cx63%5Cx72%5Cx69%5Cx70%5Cx74%5Cx3a%5Cx61%5Cx6c%5Cx65%5Cx72%5Cx74%5Cx2833%5Cx29%27%5D%3C%2Fscript%3E"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}