package job_portal.util;

public class MailHtmlUtil {

    public static String buildVerificationEmail(String code) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body {
                        background-color: transparent;
                        font-family: Arial, sans-serif;
                        color: #E0E0E0;
                        padding: 20px;
                    }
                    .container {
                        max-width: 600px;
                        background-color: transparent;
                        margin: auto;
                        padding: 20px;
                        border-radius: 8px;
                        border: 2px solid #808080;
                    }
                    .title {
                        color: #1A3D64;
                        font-size: 28px;
                        font-weight: bold;
                        text-align: center;
                    }
                    .code-box {
                        margin-top: 20px;
                        background-color: #86B9FF;
                        color: #1E1E1E;
                        font-size: 26px;
                        padding: 12px 0;
                        text-align: center;
                        border-radius: 6px;
                        font-weight: bold;
                        width: 200px;
                        margin-left: auto;
                        margin-right: auto;
                    }
                    .message {
                        font-size: 16px;
                        margin-top: 20px;
                        text-align: center;
                        color: #808080;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="title">WELCOME TO JOB PORTAL!</div>
                    <p class="message">Your <b>ACCOUNT VERIFICATION</b> code is:</p>
                    <div class="code-box">%s</div>
                </div>
            </body>
            </html>
            """, code);
    }
}
