package vo;
/*
CREATE TABLE mvc_member (
         idx INT PRIMARY KEY AUTO_INCREMENT,
         name VARCHAR(12) NOT NULL,
         gender VARCHAR(1) NOT NULL,
         age INT NOT NULL,
         email VARCHAR(50) NOT NULL UNIQUE,
         id VARCHAR(16) NOT NULL UNIQUE,
         passwd VARCHAR(20) NOT NULL
   );
 */

public class MemberBean {
    private int idx;
    private String name;
    private String gender;
    private int age;
    private String email;
    private String id;
    private String passwd;

    public MemberBean() {}

    public MemberBean(int idx, String name, String gender, int age, String email, String id, String passwd) {
        this.idx = idx;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.email = email;
        this.id = id;
        this.passwd = passwd;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public String toString() {
        return "MemberBean{" +
                "idx=" + idx +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                ", passwd='" + passwd + '\'' +
                '}';
    }
}
