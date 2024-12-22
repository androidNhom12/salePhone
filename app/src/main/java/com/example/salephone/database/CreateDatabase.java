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


        sqLiteDatabase.execSQL("DELETE FROM Products");
        sqLiteDatabase.execSQL("DELETE FROM users");
        sqLiteDatabase.execSQL("DELETE FROM orders");
        sqLiteDatabase.execSQL("DELETE FROM order_details");

        insertData(sqLiteDatabase);

    }


    public void insertData(SQLiteDatabase sqLiteDatabase){
        //Thêm data vào các bảng
        // Thêm dữ liệu vào bảng Users
        String insertUsers = "INSERT OR REPLACE INTO Users (username, password, phone_number, role, created_at) VALUES " +
                "('customer1', 'password1', '0123456789', 'customer', datetime('now')), " +
                "('customer2', 'password2', '0987654321', 'customer', datetime('now'));";

        // Thêm dữ liệu vào bảng Products
        String insertProducts = "INSERT OR REPLACE INTO Products (name, price, description, brand, image_url) VALUES " +
                "('iPhone 13', 17390, 'Apple iPhone 13 with 128GB storage', 'Iphone', " +
                "'iphone13'), " +

                "('Samsung Galaxy S22 Ultra', 15000, 'Samsung flagship phone with 256GB storage', 'Samsung', " +
                "'samsungs22ultra'), " +

                "('Iphone 15 Pro Max', 28990, 'Apple Iphone 15 Pro Max', 'Iphone', " +
                "'iphone15promax'), " +

                "('Xiaomi 14T', 13990, 'Affordable flagship phone from Xiaomi', 'Xiaomi', " +
                "'xiaomi14t'), " +

                "('Iphone 16 Pro Max', 39490, 'Apple Iphone 16 Pro Max', 'Iphone', " +
                "'iphone16promax'), " +

                "('Xiaomi Redmi Note 12', 4990, 'Compact phone with excellent camera', 'Xiaomi', " +
                "'xiaomiredminote12'), " +

                "('Oppo A38', 4390, 'Premium build with flagship features', 'Oppo', " +
                "'oppoa38'), " +

                "('Xiaomi 13 Lite', 8590, 'Affordable flagship killer', 'Xiaomi', " +
                "'xiaomi13lite'), " +

                "('Vivo V25 Pro', 7690, 'Flagship phone with Zeiss optics', 'Vivo', " +
                "'vivov25pro'), " +

                "('Samsung Galaxy A55', 13990, 'Gaming phone with high refresh rate', 'Samsung', " +
                "'samsunggalaxya55'), " +

                "('Samsung Galaxy Z Fold 6', 41990, 'Budget phone with long battery life', 'Samsung', " +
                "'samsunggalaxyzfold6'), " +

                "('Oppo A3', 6490, 'Huawei flagship with advanced camera', 'Oppo', " +
                "'oppoa3'), " +

                "('Oppo Reno 8', 7490, 'Gaming phone with great performance', 'Oppo', " +
                "'opporeno8'), " +

                "('Oppo Reno 12 5G', 16090, 'Under-display camera phone', 'Oppo', " +
                "'opporeno12'), " +

                "('Oppo A58', 4690, 'Google top-tier phone with AI features', 'Oppo', " +
                "'oppoa58'), " +

                "('Xiaomi 14T Pro', 17990, 'Slim and lightweight phone', 'Xiaomi', " +
                "'xiaomi14tpro'), " +

                "('IPhone 11', 9890, 'Compact iPhone with powerful chip', 'Iphone', " +
                "'iphone11'), " +

                "('Xiaomi Redmi 10C', 3050, 'Mid-range phone with excellent display', 'Xiaomi', " +
                "'xiaomiredmi10c'), " +

                "('Iphone 14 Plus', 22490, 'Affordable performance phone', 'Iphone', " +
                "'iphone14plus'), " +

                "('Iphone 12 Pro', 23990, 'Unique design with transparent back', 'Iphone', " +
                "'iphone12pro');";

        // Thêm dữ liệu vào bảng Orders
        String insertOrders = "INSERT OR REPLACE INTO Orders (user_id, date, status, amount) VALUES " +
                "(2, datetime('now'), 'completed', 1599.98), " +
                "(3, datetime('now'), 'pending', 699.99), " +
                "(2, datetime('now'), 'cancelled', 999.99);";

        // Thêm dữ liệu vào bảng Order_Details
        String insertOrderDetails = "INSERT OR REPLACE INTO Order_Details (order_id, product_id, quantity, price) VALUES " +
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
