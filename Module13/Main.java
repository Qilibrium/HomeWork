package Module13;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

public class Main {
    private static final String USERS_URL = "https://jsonplaceholder.typicode.com/users";
    private static final String POSTS_URL = "https://jsonplaceholder.typicode.com/posts";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) throws IOException, InterruptedException {
        
        Users defaultUser = createDefaultUser();
        //add new user
        Users createdUser = Methods.createUser(URI.create(USERS_URL), defaultUser);
        System.out.println("Created user:\n" + createdUser);
        //get user to update
        Users userToUpdate = Methods.getUserById(URI.create(USERS_URL), 7);
        //update name
        userToUpdate.setName("Oleksandr");
        //update user
        Users updatedUser = Methods.updateUser(URI.create(USERS_URL), userToUpdate);
        System.out.println("Updated user name:\n" + updatedUser.getName());
        System.out.println("Updated user:\n" + updatedUser);
        //delete user
        int deleteUserStatusCode = Methods.deleteUser(URI.create(USERS_URL), createdUser.getId());
        System.out.println("Delete user status code " + deleteUserStatusCode);
        //get all users
        List<Users> allUsers = Methods.getUsers(URI.create(USERS_URL));
        System.out.println("All users:\n" + allUsers);
        //get user by ID
        Users userById = Methods.getUserById(URI.create(USERS_URL), 9);
        System.out.println("User got by ID:\n" + userById);
        //get user by username
        Users userByUsername = Methods.getUserByUsername(URI.create(USERS_URL), "Bret");
        System.out.println("User got by username:\n" + userByUsername);

        //get user posts
        int userId = 5;
        List<Posts> userPosts = Methods.getUserPosts(URI.create(USERS_URL), userId);
        System.out.println("User posts: \n" + userPosts);
        //get user last post id
        int lastPostId = Methods.getUserLastPostId(userPosts);
        System.out.println("User last post id: " + lastPostId);
        //get comments to last user post
        List<Comments> userLastPostComments = Methods.getUserLastPostComments(URI.create(POSTS_URL), lastPostId);
        //write comments to file
        String fileJSON = GSON.toJson(userLastPostComments);
        Methods.fileWriter(userId, lastPostId, fileJSON);
        //get user open tasks
        List<Task> userOpenTasks = Methods.getOpenTasks(URI.create(USERS_URL), userId);
        System.out.println("User open tasks:\n" + GSON.toJson(userOpenTasks));
    }

    public static Users createDefaultUser() {
        Users user = new Users();
        user.setId(5);
        user.setName("Oleksandr");
        user.setUsername("AlexF");
        user.setEmail("OF@.com");
        Address address = new Address();
        address.setStreet("Tolstogo");
        address.setSuite("Apt.50");
        address.setCity("Kyiv");
        address.setZipcode("0933");
        Geo geo = new Geo();
        geo.setLat(98.98f);
        geo.setLng(-98.78f);
        address.setGeo(geo);
        user.setAddress(address);
        user.setPhone("+3909809898");
        user.setWebsite("keokef.org");
        Company company = new Company();
        company.setName("Roshen");
        company.setCatchPhrase("count");
        company.setBs("String");
        user.setCompany(company);
        return user;
    }
}
