package com.example.salephone.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.salephone.entity.CartItem;
import com.example.salephone.entity.Product;
import com.example.salephone.entity.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CreateDatabase extends SQLiteOpenHelper {
    public static final String TB_USER = "Users";
    public static final String TB_PRODUCTS = "Products";
    public static final String TB_ORDERS = "Orders";
    public static final String TB_ORDERS_DETAILS = "Order_Details";

    public static final String  TB_USER_ID = "user_id";
    public static final String  TB_USER_USERNAME = "username";
    public static final String  TB_USER_PASSWORD = "password";
    public static final String  TB_USER_PHONE_NUMBER = "phone_number";
    public static final String  TB_USER_ROLE = "role";
    public static final String  TB_USER_CREATED_AT= "created_at";

    public static final String TB_PRODUCTS_ID = "product_id";
    public static final String TB_PRODUCTS_NAME = "name";
    public static final String TB_PRODUCTS_BRAND = "brand";
    public static final String TB_PRODUCTS_PRICE = "price";
    public static final String TB_PRODUCTS_DESCRIPTION= "description";
    public static final String TB_PRODUCTS_IMAGE_URL = "image_url";

    public static final String TB_ORDERS_ID = "order_id";
    public static final String TB_ORDERS_USERID = "user_id";
    public static final String TB_ORDERS_DATE = "date";
    public static final String TB_ORDERS_STATUS = "status";
    public static final String TB_ORDERS_AMOUNT = "amount";

    public static final String TB_ORDERS_DETAILS_ID = "oder_detail_id";
    public static final String TB_ORDERS_ORDERS_ID = "order_id";
    public static final String TB_ORDERS_PRODUCT_ID = "product_id";
    public static final String TB_ORDERS_QUANTITY = "quantity";
    public static final String TB_ORDERS_PRICE = "price";


    public CreateDatabase(@Nullable Context context) {
        super(context,"SalePhone",null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Tạo bảng Users
        String tbUser = "CREATE TABLE " + TB_USER + " ( " +
                TB_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TB_USER_USERNAME + " TEXT NOT NULL UNIQUE, " +
                TB_USER_PASSWORD + " TEXT NOT NULL, " +
                TB_USER_PHONE_NUMBER + " TEXT, " +
                TB_USER_ROLE + " TEXT CHECK(" + TB_USER_ROLE + " IN ('admin', 'customer')) DEFAULT 'customer', " +
                TB_USER_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                " );";
        // Tạo bảng Products
        String tbProducts = "CREATE TABLE " + TB_PRODUCTS + " ( " +
                TB_PRODUCTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TB_PRODUCTS_NAME + " TEXT NOT NULL, " +
                TB_PRODUCTS_BRAND + " TEXT NOT NULL, " +
                TB_PRODUCTS_PRICE + " REAL NOT NULL, " +
                TB_PRODUCTS_DESCRIPTION + " TEXT, " +
                TB_PRODUCTS_IMAGE_URL + " TEXT" +
                " );";
        // Tạo bảng Orders
        String tbOrders = "CREATE TABLE " + TB_ORDERS + " ( " +
                TB_ORDERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TB_ORDERS_USERID + " INTEGER NOT NULL, " +
                TB_ORDERS_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                TB_ORDERS_STATUS + " TEXT CHECK(" + TB_ORDERS_STATUS + " IN ('pending', 'completed', 'cancelled')) DEFAULT 'pending', " +
                TB_ORDERS_AMOUNT + " REAL NOT NULL, " +
                "FOREIGN KEY (" + TB_ORDERS_USERID + ") REFERENCES " + TB_USER + "(" + TB_USER_ID + ")" +
                " );";
        // Tạo bảng Order_Details
        String tbOrderDetails = "CREATE TABLE " + TB_ORDERS_DETAILS + " ( " +
                TB_ORDERS_DETAILS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TB_ORDERS_ORDERS_ID + " INTEGER NOT NULL, " +
                TB_ORDERS_PRODUCT_ID + " INTEGER NOT NULL, " +
                TB_ORDERS_QUANTITY + " INTEGER NOT NULL, " +
                TB_ORDERS_PRICE + " REAL NOT NULL, " +
                "FOREIGN KEY (" + TB_ORDERS_ORDERS_ID + ") REFERENCES " + TB_ORDERS + "(" + TB_ORDERS_ID + "), " +
                "FOREIGN KEY (" + TB_ORDERS_PRODUCT_ID + ") REFERENCES " + TB_PRODUCTS + "(" + TB_PRODUCTS_ID + ")" +
                " );";
        sqLiteDatabase.execSQL(tbUser);
        // Thêm tài khoản admin mặc định
        String insertAdmin = "INSERT INTO Users (username, password, phone_number, role, created_at) " +
                "VALUES ('admin', 'admin', '0000000000', 'admin', datetime('now'))";
        sqLiteDatabase.execSQL(tbProducts);
        sqLiteDatabase.execSQL(tbOrders);
        sqLiteDatabase.execSQL(tbOrderDetails);


        sqLiteDatabase.execSQL("DELETE FROM products");
        sqLiteDatabase.execSQL("DELETE FROM users");
        sqLiteDatabase.execSQL("DELETE FROM orders");
        sqLiteDatabase.execSQL("DELETE FROM order_details");

        insertData(sqLiteDatabase);

    }


    public void insertData(SQLiteDatabase sqLiteDatabase){
        //Thêm data vào các bảng
        // Thêm dữ liệu vào bảng Users
        String insertUsers = "INSERT INTO Users (username, password, phone_number, role, created_at) VALUES " +
                "('customer1', 'password1', '0123456789', 'customer', datetime('now')), " +
                "('customer2', 'password2', '0987654321', 'customer', datetime('now'));";

        // Thêm dữ liệu vào bảng Products
        String insertProducts = "INSERT INTO Products (name, price, description, brand, image_url) VALUES " +
                "('iPhone 13', 799.99, 'Apple iPhone 13 with 128GB storage', 'Iphone', " +
                "'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone-13_2_.png'), " +

                "('Samsung Galaxy S22', 999.99, 'Samsung flagship phone with 256GB storage', 'Samsung', " +
                "'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone-15-pro-max_3.png'), " +

                "('Google Pixel 7', 699.99, 'Google Pixel phone with great camera', 'Iphone', " +
                "'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone-13_2_.png'), " +

                "('Xiaomi Mi 12', 599.99, 'Affordable flagship phone from Xiaomi', 'Xiaomi', " +
                "'https://example.com/mi12.jpg'), " +

                "('OnePlus 11', 749.99, 'Fast and smooth performance phone', 'Iphone', " +
                "'https://example.com/oneplus11.jpg'), " +

                "('Sony Xperia 5 IV', 899.99, 'Compact phone with excellent camera', 'Iphone', " +
                "'https://example.com/xperia5.jpg'), " +

                "('Oppo Find X5', 649.99, 'Premium build with flagship features', 'Oppo', " +
                "'https://example.com/findx5.jpg'), " +

                "('Realme GT 2 Pro', 549.99, 'Affordable flagship killer', 'Iphone', " +
                "'https://example.com/gt2pro.jpg'), " +

                "('Vivo X80', 799.99, 'Flagship phone with Zeiss optics', 'Vivo', " +
                "'data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxISEhUSEhMVFRUVGBUXFRUXFRUXFRcXFRcXFxUVFxUYHSggGBolGxcVITEhJSkrLi4uFx8zODMtNygtLisBCgoKDg0OGBAQGyslHSU3Ky8tLS8tLy0tLS0rKy0tLS0tLS0tLS0tLS0tKy0tLS0rLS0rLS0tLS0tLS0tLS0tLf/AABEIAOYA2wMBIgACEQEDEQH/xAAcAAABBQEBAQAAAAAAAAAAAAACAAEDBAYHBQj/xABLEAABAgQCAwoICwYGAwAAAAABAAIDBBEhEjETQVEFBgcUImFxgZGhJDJ0krGys9EjNFJTVHJzk8HS8BVCQ0Ri8RYzgqLC4Rclg//EABkBAQADAQEAAAAAAAAAAAAAAAABAgMEBf/EACwRAQACAgAFAgUEAwEAAAAAAAABAgMREhMhMTIUUUFSYZHwIqGx4QRxgWL/2gAMAwEAAhEDEQA/AO4qnFzKWlO1TsYCASEClsutDM6kMY0NBZPB5Wd0AQM1ZfkehBFaAKixULYhJF0AK63IIdENiruiGuaBR/GKllsinhtBFTcoIxw5WQFM5dahg5hHBOI0N1I9gAqAglVBHpTtVnRDYgeFkOhV5nPqTPeQSAVLCbUVN0AyuvqUkxkgjcmlLIYTiTQ3QRw8x0hXVG5gAJoq+lO1ALs1ageKE4hjYoIjiDQGgQPM5hKWz6kUEYs7pRhhFRZBJG8UqoFJDcSaHJTmGNiA1TiZnpKWlO1WGMBAJCBuLjnUZikWGpFxjmTGFivtQO1uK56LJOGDLXtSDsFs9aR5fNRAzXl1iiMEC97IRDw3T6atqZ2QDxg8yPQg3uh4vzp9PS1EDOiFthqTtGPPVsTGHiv+ticHBbOqBObhuOi6YRS6x1py7HbLWmELDfYgLQDnQcYPMi4xzJuL86AhCBve6FzsNh3pxFpamSYsx3yQO3l56tiTmYbhIcjnqkX4rZIBEYm1r27UfFxzodDS9crp+McyAdOeZG2GHXOtDoK604iYbbEDOOCw70mnHY9yRGO+SQbgvmgJ0MNuNSDTnmRGJittTcX50BcXHOg" +
                "MYi1rWRcY5k2hreud0AaF2z0KZkQAUOYUmIbVViC5QHEbiNQlC5OdkUuaC6aYvSiB4jw4UGao7ozbJaG6NGOFjLk+gAayVagi6yvCe0PgyzDdr5yWa4aiC7Iq1K8VoiVbTqJlTicK8qHFphRqjbgB73Kt/wCUpY30UXth/mXKN8h8MmftonrFU2L0I/xsbm5tnaYfCrLAU0EbthfmQxeFKWP8GL2w/wAy4+xGE9NjOdd1yFwpSwvoY3bD/MjfwqyxBGgjdsP8y5CmKemxnOs69I8I0OM90ODKxnuax0R3KhNY1rdb3F1h1LwYnDkyp8GH3jj3hi87g0ZVm6YFATAgNBP9Wn7slz5+9h9SNIzPY5c98X6pitezWLxqOKXTHcNrD/Ljz3/kRM4cWj+WHnu/IuY/4Zf84zscl/haJ84zsd7lHJv8v59zmV+b8+zpsThwYf5Yee/8qZnDcwH4sPPf+Vc1ZvXig2is7He5SnevGNjGZ2O9yjlX+X8+5zKfM6Q7hybT4sPPf+RRf+bGfRh57/yrnkPelFBBEVlRQizswiO9SLixaWHX6rvRROVf5fz7nMp8zo44cm/Rh57/AMi029Hf6N0Ku0BhtDms0geHNDnUDQ5pAc2psDcVXD/8HxPnWdjl0rgs3HfLy83ie1wc2ooCKFtTW/V2KJx2iNzX8+6YvWZ6S67COGxslFOKwTTFzZNL2N1g1JjCDU5KUxm7fSlFNiqwadiAtC7Z6FM2IAKE5KTENqqPFz0oBVuFkE+AbB2KtEcQTcoCmc+pPK60UAVF79KaPalLdCA4/irF8IB5Ep5dK+sVroJqaG6y/CUKMk6D+elfXV8flCt/GXDt8R8Nmvt43tCqjCp98LvDZvyiP7RyghDkxTrbCiuHS1tivV3qu3JrcpmOG1vnN96kDxtb5zfessG2rXvvsyrUpmivpz5uc/qy4/V29m3Jj3avGNrfOb70DojflN85vvWbgua2I3G3E0PaXNrTE0OBc0Gtqi3Xmvc3WZBM2WwpcwGAwgYLzjNaOxOqCaVsaV1J6q3sjkx7t7wVmo3SoQfgpbIg64+sLx4rOUelWuA81hbo1+bl/TGQOZcrbBfj4rf6/hlnjW" +
                "oQNYpWsUjWKQMW7BCGqQMUzYakENEK4YjENWAxEIaJVhDWu3qDweZ+zd6Fm8C1O9dvwEx9QrHP4S0w+cOgQNfSmmcutDHNDa3QlANTe/SvMegCD4wVooIrQAaKuHnaUAK7DyHQEsA2DsVZ7jU3KB9OVK2GCKnWh4vz9ybS4bUyQKI7CaBKGcWafDjvlqSpg56oHewNFRmsbwjxCYcn5dKeutjpMVsljuEqHRkl5fKeur4/KFbdnDd8R8Nm/KI/tHKsw2c3LExza7MQp2ZdpUu+A+GTflEf2rlVaV6ut105OyiNzn7R2ohuY/aF6LSjDlj6Wn1X51nmN3NiAggioIOdrKZ8OMXY+TU0/eOoEfiVexJnFPS0+pzbN1wNS+CDP2uWwgb7MdPWKjezlHpVzgjvDnx/TD/H3IHMuUxxFbWiPp/DPLO9TKBjFMGKRjFKGLZiibDRBilDUQYgjDU+FTiHzIxDUbFbCtNveFJeYP8AQfQvDwLQbht8Hjj+hyxzz+hrh823Y3FWupNEGG4SLsPPW6Vcdsl5zvCyISaHJSaEIdFhvWtE3GObvQDpypWwgRU60PF+fuTaalqZWQHpxzqN0Im41oNGdhViG4AAEoAY7DYpP5eWpDGFTa6eBbO3SgTWFtyshwmRAWSXl8p662UVwIoLrC8JLD/68EZz0t6wWmLyhW/Zw7fAfDJvyiP7RyqNKsb4D4ZN+UR/aOVMFepXs5ZWGlGHKzuBGgNjNMwAYVHVBaXCpFjQc/T+I9mXnNzQG4oZJDMLuQ+7/g3YvGzrpW9FFE31PaTTPYkLnLUMndzMTawxTHEJ+Df4hfih1vU8nk0G3VcmOVn9zaQ8cK4DQ+rHOBoxuJxANyXGIOppUcyfaTh+rQ8DXiz/ANWD34/+1ZdDuelU+Bcj/wBjT5EvTorGXt6EVOazif12/wCfwjJHSFJsNSthq4yX2KUSx2K/Ey0paJGyCrggKRsuq8RpTENFo1ebLhOJcKOJPDKhgXtblWgRybAMJ7lWMBWzDAk5quWhidmErPLbddNMcas1724skmDDcrG8He+iJMvjwo2Goe6JCIJqGOwvwOqT4oiQ6cxpqW0jmotdcd6zWdS7K2i0b" +
                "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'), " +

                "('Asus ROG Phone 6', 1099.99, 'Gaming phone with high refresh rate', 'Iphone', " +
                "'data:image/jpeg;base64,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'), " +

                "('Nokia G21', 199.99, 'Budget phone with long battery life', 'Iphone', " +
                "'data:image/jpeg;base64,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'), " +

                "('Huawei P50 Pro', 899.99, 'Huawei flagship with advanced camera', 'Iphone', " +
                "'https://samcenter.vn/images/thumbs/0005392_samsung-galaxy-a15-8gb-128gb.png'), " +

                "('Lenovo Legion Phone Duel', 799.99, 'Gaming phone with great performance', 'Iphone', " +
                "'https://example.com/legionduel.jpg'), " +

                "('ZTE Axon 40 Ultra', 649.99, 'Under-display camera phone', 'Iphone', " +
                "'https://example.com/axon40ultra.jpg'), " +

                "('Google Pixel 7 Pro', 899.99, 'Google top-tier phone with AI features', 'Iphone', " +
                "'https://example.com/pixel7pro.jpg'), " +

                "('Motorola Edge 30', 499.99, 'Slim and lightweight phone', 'Iphone', " +
                "'https://example.com/edge30.jpg'), " +

                "('iPhone SE (2022)', 429.99, 'Compact iPhone with powerful chip', 'Iphone', " +
                "'https://example.com/iphonese2022.jpg'), " +

                "('Samsung Galaxy A53', 349.99, 'Mid-range phone with excellent display', 'Samsung', " +
                "'https://example.com/galaxya53.jpg'), " +

                "('Poco F4', 399.99, 'Affordable performance phone', 'Iphone', " +
                "'https://example.com/pocof4.jpg'), " +

                "('Iphone 16 Pro Max', 499.99, 'Unique design with transparent back', 'Iphone', " +
                "'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNZnLRuCvYgmvh-T8pSVGF8BWebGwBr-IxUw&s');";

        // Thêm dữ liệu vào bảng Orders
        String insertOrders = "INSERT INTO Orders (user_id, date, status, amount) VALUES " +
                "(2, datetime('now'), 'completed', 1599.98), " +
                "(3, datetime('now'), 'pending', 699.99), " +
                "(2, datetime('now'), 'cancelled', 999.99);";

        // Thêm dữ liệu vào bảng Order_Details
        String insertOrderDetails = "INSERT INTO Order_Details (order_id, product_id, quantity, price) VALUES " +
                "(1, 1, 1, 799.99), " +
                "(1, 2, 1, 799.99), " +
                "(2, 3, 1, 699.99), " +
                "(3, 2, 1, 999.99);";

        // Kiểm tra nếu bảng chưa có dữ liệu thì mới thêm
        String checkProducts = "SELECT COUNT(*) FROM Products;";
        Cursor cursor = sqLiteDatabase.rawQuery(checkProducts, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if(count == 0){
            sqLiteDatabase.execSQL(insertProducts);
            sqLiteDatabase.execSQL(insertOrders);
            sqLiteDatabase.execSQL(insertOrderDetails);
            sqLiteDatabase.execSQL(insertUsers);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public SQLiteDatabase open() {
        return this.getWritableDatabase();
    }
    public void ensureAdminAccountExists() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Kiểm tra xem tài khoản admin đã tồn tại hay chưa
        String query = "SELECT * FROM Users WHERE username = ?";
        Cursor cursor = db.rawQuery(query, new String[]{"admin"});
        if (cursor.getCount() == 0) {
            // Nếu chưa tồn tại, thêm admin
            String insertAdmin = "INSERT INTO Users (username, password, phone_number, role, created_at) " +
                    "VALUES ('admin', 'admin', '0000000000', 'admin', datetime('now'))";
            db.execSQL(insertAdmin);
        }
        cursor.close();
    }


    //Check exist user in database
    public boolean isUserExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Users WHERE username = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    //Check username and password
    public boolean isValidUser(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Users WHERE username = ? and password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{user.getUser_username(), user.getUser_password()});
        boolean inValid = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return inValid;
    }
    // Login
    public User login(User user) {
        User res = new User();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Users WHERE username = ? and password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{user.getUser_username(), user.getUser_password()});
        if (cursor != null && cursor.moveToFirst()) {
            // Nếu tìm thấy user, ánh xạ dữ liệu từ Cursor sang đối tượng User
            res = new User();
            res.setUser_id(cursor.getInt(cursor.getColumnIndexOrThrow("user_id"))); // Ánh xạ cột "id"
            res.setUser_username(cursor.getString(cursor.getColumnIndexOrThrow("username"))); // Ánh xạ cột "username"
            res.setUser_password(cursor.getString(cursor.getColumnIndexOrThrow("password"))); // Ánh xạ cột "password"
            res.setUser_role(cursor.getString(cursor.getColumnIndexOrThrow("role")));
            res.setUser_create_at(cursor.getString(cursor.getColumnIndexOrThrow("created_at")));
            res.setUser_phone(cursor.getString(cursor.getColumnIndexOrThrow("phone_number")));
        }

        if (cursor != null) {
            cursor.close(); // Đóng Cursor để giải phóng tài nguyên
        }
        db.close(); // Đóng Database

        return res;

    }

    // add user to database
    public long insertUser(User user) {
        ContentValues values = new ContentValues();
        values.put("username", user.user_username);
        values.put("password", user.user_password);
        values.put("role", user.user_role);
        // Lấy ngày giờ hiện tại theo định dạng 'YYYY-MM-DD' (ví dụ: "2024-11-19")
        String dateNow = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        values.put("created_at", dateNow);
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert("Users", null, values);
    }

    // add product to database
    public boolean addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",product.getName());
        values.put("price",product.getPrice());
        values.put("description",product.getDescription());
        values.put("image_url", product.getImage_url());

        long result = db.insert("Products",null,values);
        db.close();
        return result != -1;
    }

    // get all product
    public List<Product> getAllProduct() {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM Products";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor != null) {
            while (cursor.moveToNext()) {
                // Đọc dữ liệu từ cursor và tạo đối tượng Product
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("product_id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String price = cursor.getString(cursor.getColumnIndexOrThrow("price"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow("image_url"));

                // Tạo đối tượng Product và thêm vào danh sách
                Product product = new Product(id, name, price, description, imageUrl);
                products.add(product);
            }
            cursor.close();
        }
        db.close();
        return products;
    }
    public boolean updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name",product.getName());
        values.put("price", product.getPrice());
        values.put("description", product.getDescription());

        int rows = db.update("Products", values,"product_id = ?", new String[]{String.valueOf(product.getProduct_id())});
        db.close();
        return rows > 0;
    }

    public  boolean deleteSelectedProducts(List<Product> selectedProducts) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Tạo điều kiện WHERE để xóa các sản phẩm đã chọn
        StringBuilder whereClause = new StringBuilder("product_id IN (");
        for (int i = 0; i < selectedProducts.size(); i++) {
            whereClause.append("?");
            if (i < selectedProducts.size() - 1) {
                whereClause.append(", ");
            }
        }
        whereClause.append(")");

        // Lấy danh sách các ID sản phẩm đã chọn
        List<String> productIds = new ArrayList<>();
        for (Product product : selectedProducts) {
            productIds.add(String.valueOf(product.getProduct_id()));
        }

        // Xóa các sản phẩm đã chọn
        int rows = db.delete("Products", whereClause.toString(), productIds.toArray(new String[0]));
        db.close();
        return rows > 0; // Trả về true nếu có sản phẩm bị xóa
    }

    //Thêm sản phẩm vào database
//    public void addProduct(String name, double price, String description, String imageUrl) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("name", name);
//        values.put("price", price);
//        values.put("description", description);
//        values.put("image_url", imageUrl);
//        db.insert("Products", null, values);
//        db.close();
//    }

    //Lấy dữ liệu đổ lên giỏ hàng
//    public ArrayList<CartItem> loadCartItemsFromDatabase() {
//        ArrayList<CartItem> cartItems = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT name, image_url, price, quantity FROM product", null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                String name = cursor.getString(0);
//                String imageUrl = cursor.getString(1); // Lấy URL hình ảnh
//                int price = cursor.getInt(2);
//                int quantity = cursor.getInt(3);
//                cartItems.add(new CartItem(name, imageUrl, price, quantity));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return cartItems;
//    }

}
