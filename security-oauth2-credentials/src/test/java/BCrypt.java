import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCrypt {
    public static void main(String[] args) {
        String password = "123456";
        String username = "admin";
        BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
        String passwordBCrypt = bpe.encode(password + username);
        System.out.println("BCrypt加密:" + passwordBCrypt);
        System.out.println("对比结果：" + bpe.matches(password + username, passwordBCrypt));
    }
}
