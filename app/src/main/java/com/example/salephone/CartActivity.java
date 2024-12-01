package com.example.salephone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salephone.adapter.CartAdapter;
import com.example.salephone.entity.CartItem;
import com.example.salephone.entity.Product;

public class CartActivity extends AppCompatActivity {
    ImageView iconAccount, iconHome, iconPhone;
    private RecyclerView recyclerView;
    private TextView totalAmount;
    private Button checkoutButton, deleteButton;
    private Cart cart;
    private CheckBox selectAllCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cart);

        // Ánh xạ các view từ layout
        recyclerView = findViewById(R.id.cart_recycler_view);
        totalAmount = findViewById(R.id.total_amount);
        checkoutButton = findViewById(R.id.checkout_button);
        deleteButton = findViewById(R.id.delete_button);
        selectAllCheckbox = findViewById(R.id.select_all_checkbox);

        // Khởi tạo giỏ hàng (lấy từ database hoặc singleton)
        cart = new Cart();

        addDefaultItemsToCart();

        // Set LayoutManager cho RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Hiển thị danh sách sản phẩm trong giỏ hàng
        CartAdapter adapter = new CartAdapter(this, cart.getCartItems(), this::updateTotalAmount);
        recyclerView.setAdapter(adapter);

        // Hiển thị tổng giá
        updateTotalAmount();

        // Xử lý sự kiện chọn tất cả
        selectAllCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            for (CartItem item : cart.getCartItems()) {
                item.setSelected(isChecked);
            }
            adapter.notifyDataSetChanged();
            updateTotalAmount();
        });

        // Xử lý sự kiện xóa sản phẩm đã chọn
        deleteButton.setOnClickListener(v -> {
            cart.removeSelectedItems();
            adapter.notifyDataSetChanged();
            updateTotalAmount();
        });

        // Xử lý sự kiện thanh toán
        checkoutButton.setOnClickListener(v -> {
            if (cart.getCartItems().stream().anyMatch(CartItem::isSelected)) {
                // Thực hiện thanh toán (ví dụ chuyển sang màn hình thanh toán)
                Toast.makeText(this, "Đang xử lý", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Chọn ít nhất 1 sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });

        // Cập nhật trạng thái nút "Chọn tất cả" khi giỏ hàng thay đổi
        updateSelectAllCheckboxVisibility();
    }

    // Cập nhật tổng tiền và trạng thái của nút thanh toán
    public void updateTotalAmount() {
        double total = cart.getTotalPrice();
        totalAmount.setText("Tổng: " + total + " VND");

        // Kiểm tra xem có sản phẩm nào được chọn để kích hoạt nút thanh toán
        boolean anySelected = cart.getCartItems().stream().anyMatch(CartItem::isSelected);
        checkoutButton.setEnabled(anySelected);
        checkoutButton.setAlpha(anySelected ? 1.0f : 0.5f);

        // Cập nhật trạng thái nút "Chọn tất cả"
        updateSelectAllCheckboxVisibility();
    }

    // Kiểm tra và ẩn/hiện nút "Chọn tất cả" khi giỏ hàng có sản phẩm
    private void updateSelectAllCheckboxVisibility() {
        if (cart.getCartItems().isEmpty()) {
            selectAllCheckbox.setVisibility(View.GONE); // Ẩn nếu không có sản phẩm
        } else {
            selectAllCheckbox.setVisibility(View.VISIBLE); // Hiện nếu có ít nhất 1 sản phẩm
        }
    }

    // Thêm 2 sản phẩm mặc định vào giỏ hàng
    private void addDefaultItemsToCart() {
        // Tạo sản phẩm 1
        Product product1 = new Product(1, "Samsung", "145000", "abc",
                "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxISEhUSEhMVFRUVGBUXFRUXFRUXFRcXFRcXFxUVFxUYHSggGBolGxcVITEhJSkrLi4uFx8zODMtNygtLisBCgoKDg0OGBAQGyslHSU3Ky8tLS8tLy0tLS0rKy0tLS0tLS0tLS0tLS0tKy0tLS0rLS0rLS0tLS0tLS0tLS0tLf/AABEIAOYA2wMBIgACEQEDEQH/xAAcAAABBQEBAQAAAAAAAAAAAAACAAEDBAYHBQj/xABLEAABAgQCAwoICwYGAwAAAAABAAIDBBEhEjETQVEFBgcUImFxgZGhJDJ0krGys9EjNFJTVHJzk8HS8BVCQ0Ri8RYzgqLC4Rclg//EABkBAQADAQEAAAAAAAAAAAAAAAABAgMEBf/EACwRAQACAgAFAgUEAwEAAAAAAAABAgMREhMhMTIUUUFSYZHwIqGx4QRxgWL/2gAMAwEAAhEDEQA/AO4qnFzKWlO1TsYCASEClsutDM6kMY0NBZPB5Wd0AQM1ZfkehBFaAKixULYhJF0AK63IIdENiruiGuaBR/GKllsinhtBFTcoIxw5WQFM5dahg5hHBOI0N1I9gAqAglVBHpTtVnRDYgeFkOhV5nPqTPeQSAVLCbUVN0AyuvqUkxkgjcmlLIYTiTQ3QRw8x0hXVG5gAJoq+lO1ALs1ageKE4hjYoIjiDQGgQPM5hKWz6kUEYs7pRhhFRZBJG8UqoFJDcSaHJTmGNiA1TiZnpKWlO1WGMBAJCBuLjnUZikWGpFxjmTGFivtQO1uK56LJOGDLXtSDsFs9aR5fNRAzXl1iiMEC97IRDw3T6atqZ2QDxg8yPQg3uh4vzp9PS1EDOiFthqTtGPPVsTGHiv+ticHBbOqBObhuOi6YRS6x1py7HbLWmELDfYgLQDnQcYPMi4xzJuL86AhCBve6FzsNh3pxFpamSYsx3yQO3l56tiTmYbhIcjnqkX4rZIBEYm1r27UfFxzodDS9crp+McyAdOeZG2GHXOtDoK604iYbbEDOOCw70mnHY9yRGO+SQbgvmgJ0MNuNSDTnmRGJittTcX50BcXHOgMYi1rWRcY5k2hreud0AaF2z0KZkQAUOYUmIbVViC5QHEbiNQlC5OdkUuaC6aYvSiB4jw4UGao7ozbJaG6NGOFjLk+gAayVagi6yvCe0PgyzDdr5yWa4aiC7Iq1K8VoiVbTqJlTicK8qHFphRqjbgB73Kt/wCUpY30UXth/mXKN8h8MmftonrFU2L0I/xsbm5tnaYfCrLAU0EbthfmQxeFKWP8GL2w/wAy4+xGE9NjOdd1yFwpSwvoY3bD/MjfwqyxBGgjdsP8y5CmKemxnOs69I8I0OM90ODKxnuax0R3KhNY1rdb3F1h1LwYnDkyp8GH3jj3hi87g0ZVm6YFATAgNBP9Wn7slz5+9h9SNIzPY5c98X6pitezWLxqOKXTHcNrD/Ljz3/kRM4cWj+WHnu/IuY/4Zf84zscl/haJ84zsd7lHJv8v59zmV+b8+zpsThwYf5Yee/8qZnDcwH4sPPf+Vc1ZvXig2is7He5SnevGNjGZ2O9yjlX+X8+5zKfM6Q7hybT4sPPf+RRf+bGfRh57/yrnkPelFBBEVlRQizswiO9SLixaWHX6rvRROVf5fz7nMp8zo44cm/Rh57/AMi029Hf6N0Ku0BhtDms0geHNDnUDQ5pAc2psDcVXD/8HxPnWdjl0rgs3HfLy83ie1wc2ooCKFtTW/V2KJx2iNzX8+6YvWZ6S67COGxslFOKwTTFzZNL2N1g1JjCDU5KUxm7fSlFNiqwadiAtC7Z6FM2IAKE5KTENqqPFz0oBVuFkE+AbB2KtEcQTcoCmc+pPK60UAVF79KaPalLdCA4/irF8IB5Ep5dK+sVroJqaG6y/CUKMk6D+elfXV8flCt/GXDt8R8Nmvt43tCqjCp98LvDZvyiP7RyghDkxTrbCiuHS1tivV3qu3JrcpmOG1vnN96kDxtb5zfessG2rXvvsyrUpmivpz5uc/qy4/V29m3Jj3avGNrfOb70DojflN85vvWbgua2I3G3E0PaXNrTE0OBc0Gtqi3Xmvc3WZBM2WwpcwGAwgYLzjNaOxOqCaVsaV1J6q3sjkx7t7wVmo3SoQfgpbIg64+sLx4rOUelWuA81hbo1+bl/TGQOZcrbBfj4rf6/hlnjWoQNYpWsUjWKQMW7BCGqQMUzYakENEK4YjENWAxEIaJVhDWu3qDweZ+zd6Fm8C1O9dvwEx9QrHP4S0w+cOgQNfSmmcutDHNDa3QlANTe/SvMegCD4wVooIrQAaKuHnaUAK7DyHQEsA2DsVZ7jU3KB9OVK2GCKnWh4vz9ybS4bUyQKI7CaBKGcWafDjvlqSpg56oHewNFRmsbwjxCYcn5dKeutjpMVsljuEqHRkl5fKeur4/KFbdnDd8R8Nm/KI/tHKsw2c3LExza7MQp2ZdpUu+A+GTflEf2rlVaV6ut105OyiNzn7R2ohuY/aF6LSjDlj6Wn1X51nmN3NiAggioIOdrKZ8OMXY+TU0/eOoEfiVexJnFPS0+pzbN1wNS+CDP2uWwgb7MdPWKjezlHpVzgjvDnx/TD/H3IHMuUxxFbWiPp/DPLO9TKBjFMGKRjFKGLZiibDRBilDUQYgjDU+FTiHzIxDUbFbCtNveFJeYP8AQfQvDwLQbht8Hjj+hyxzz+hrh823Y3FWupNEGG4SLsPPW6Vcdsl5zvCyISaHJSaEIdFhvWtE3GObvQDpypWwgRU60PF+fuTaalqZWQHpxzqN0Im41oNGdhViG4AAEoAY7DYpP5eWpDGFTa6eBbO3SgTWFtyshwmRAWSXl8p662UVwIoLrC8JLD/68EZz0t6wWmLyhW/Zw7fAfDJvyiP7RyqNKsb4D4ZN+UR/aOVMFepXs5ZWGlGHKzuBGgNjNMwAYVHVBaXCpFjQc/T+I9mXnNzQG4oZJDMLuQ+7/g3YvGzrpW9FFE31PaTTPYkLnLUMndzMTawxTHEJ+Df4hfih1vU8nk0G3VcmOVn9zaQ8cK4DQ+rHOBoxuJxANyXGIOppUcyfaTh+rQ8DXiz/ANWD34/+1ZdDuelU+Bcj/wBjT5EvTorGXt6EVOazif12/wCfwjJHSFJsNSthq4yX2KUSx2K/Ey0paJGyCrggKRsuq8RpTENFo1ebLhOJcKOJPDKhgXtblWgRybAMJ7lWMBWzDAk5quWhidmErPLbddNMcas1724skmDDcrG8He+iJMvjwo2Goe6JCIJqGOwvwOqT4oiQ6cxpqW0jmotdcd6zWdS7K2i0bgnRA6w1qMQClDaQalTmINoVUh0451GYRN9qDRnYVYY8AC6CRU4uZQ1VuFkEAy2XWhmtSGYz6k8trQBA8ZZjhKaKSJ1ifladblro+SxfCB4sl5fKesVfH5Qrbs4FvhPhk35RMe1cqYKs74z4bN+UTHtXKkCvUr2c0pgUVVECiqrKpMSYlBVIlB1DgTFWzw/phf8ANaV0G5WY4CzV0+P6IHaTF9wW5MC5XJa2slv+fwvavSFBkI6lZZXWrIl1I2CqzeFYqriFtCmbLDnCnZDUghqk3XiqrxX9URCUV9jFKAqzeVopDzeKFTvlqy8ZrhZzHA9BFCrdAhnbS8X6pVZtMrRWIlzPeLGMLdV8M2BEMU/+BY7/AHNh9i7FLZ9S4sfgN8AyAe+nMBpz/wAWgda7ZM5dav8A5HeJ+hi7TA43ilVAjgnlBWiFztTqlEzPSU1Vch5DoCBtENige8g0BT8YOwIxCBvtQKEMQqbpovJysmc7DYdKdpx56tiAYbiTQ3Cy3COwBslT6fKeuVqzDw3CyPCHEq2S8vlPXKvj8oVv2fPW+M+GzflEx7Vyogq7vi+OTflEx7VyoBenHZzylBRAqIFFVWQOqRKCqRKIdU4BfHnvqS/piro5hXXNuAWIGunicgyX9MZdMh7owXHk4z0Q4h7wF52aZ451DorrUbIQkbYSlZFZ/UOmG8d5Cma5mpze0LCbyvFIQth9KMQ1M3DtB6wja0KvGnhQhiLApDTWR2hOKbQo45TwwiwKPdFvwEToVqir7q/F4nQprbcomHJuEdui3XhuFsTnU6TDh0/3PK7VLvx3NwRUda5Dw0MImIEX5JhEedExeoxdQ3AjVlYD8y6FDB6QwA94XXl646Sxp0taHpRGACozUIinajETFY60WgG0rmbD0TdigdEINAU/GDsCMQQb7boA4udoRCKBbYpNK3aoHwyTUZICc3HcdCTRgz17EUI4RQ2TReVldAjExWCyPCJDo2S8vlPXK1kNpBqclleEd4LZGn0+U9cq+PyhW3aXztvj+OTflEf2rlQBV3fGfDJryiP7VyoAr0oc8jT1QVRVVkHqlVNVNVB1TgIZiM+KgciXzyzjLaP3JiAmj2dRcD6qx3AF4899SX9MVdN0YXBkyTW86bcuLRG3jw5MgfCaJ3U6vaAoA2G2+PD9XSH0he2+UqOpefNyJFbZqK5N90Tj12Rw5+GP4tRsMP8AFTDdWDqe8Hmb+FV5UaXoclTjNoclpGOlmc3vVqYW67MsZPSz/tWYU005OA6liGxj+irEKcI/XUot/jx8E1zz8WyOI5GGe5HNh3F4uIAcnVRZ6Vnidf8AdevCj4peLXLCSueaTWW8WiYZ3hikQ4S+IVadKHU5tGWf81pt4UbSSMIa2mIOx7vwIXm8LTKygiAV0byabfg4hoesBBwQzuOTcTb4RxpsxNbbta5bz1wR9JZx0yy2ohYb7EWnGwp3vBFBmoRCOz0LmbC4udoRiMBbZZHpm7VA6GSagZoI6K3CNgjVOLmUBzGfUnltaOWy60M1qQHHyWL4QPFkvL5T1ytbA8ZZjhMPJkfL5T11fH5Qrbs+ct8fxya8oj+1cqAV7fGfDJryiP7Vy88L0oYSOqVUIKeqlAk1U1UqoOp8B0Utbug4Zhkt6Yy9qJvkj1NHgf6QfTVeBwLf5W6X1Jb0xkMWNcrKlK2tbcK5bTGtS99m+SZ+dPYB6Ar0vvljEXcsiIwRiborzhpPwhlGW0fFtoe6od4zWnq60RiQXZsp0OI/FYc7ou1GiifOuOZJ61T08fDovzp+LbvhyutxH+tpO3ZX+yB7JX92NQ84r+KxJmCh0xVuT/6lHN+kN5L6AfxQT9Wn47br2ZPAZeNhdiOB1bWy1LlWlO1bneREJgzFfkH8FlmxarvbTHk3OtNRv5ltJIRm87OoY2hx80lY3gOmCZeLDP7pbbXm8n1guh76IWKWjt16KJTpDSR3gLmHAvGwzkxD54opso9tPUKyp1w2hrbpeHW4IuFaJQxvFKqBczYqK3DNh0I1SiZnpKBYztKsw2ggEhNoAo3RCLDUgUY0NrdCeBetb9KdjcVyk8YctaAorQBUWWL4RXciSqf5+T9dbFry40KyHCawNhyVPp8p66vj8oRbs+dN8R8Lmft4/tHKhVXd8HxqY+3je0cqC9GOznkaSGqVVIKqVUNUkHUeBg/A7p/UlvWjLzI0c1PSV6fAq2sHdIWuyWzyzjKDiMMOOKITf91oHeT+CpimItZTLG9KIilPiXqQuLN/hl31nnto2iuQN1GM8SHDbzhra9putZv7Qz4XjwJaI/xWOPQCr8DcCZd/DI6bL0Du6/5R1ZUHo6UA3Xedff3Kk3snhqaHvYinMgdY/FWmb2dr29oUB3RPP+q1t2/rIhug7b3/AK2d6iZye5+j2Wmb3Wi2NvetHuDJNhwJjCQfg3ej+yyQm9Z/615jpK1G9iJigzFfkO7/AO/csssW4estMc14ukN61mJrgb1qL84XDuD1xg7rllaYjyukwn1/3u7l3F5w5LjBlmQN3MyMRiRG1NAMEaK7ZlhgntWWCelo+jbL3iXZYbiSAVOWDYOxA6GG3GpR6Yrmag0h2lWWMFBYJtAFGYpFtiAuMc3em0WK9c0OgKkbEAFDqQNiwWz1pVx81OtM9uI1CUMYc9aB9HhvmsXwoRawpO2U/Keutq94cKDNYjhPhkQpOv0+U9cq+PyhFuz523c+MzH20b2jlSV7d34zMfbRvaOVFehHZhJJJJKUEkkkmx1DgX/yN0/qS3rRl40WMam+v9frmXscDR+A3T+zl/TGWWjTBqVGHysrk+D0RFH62C/66FK2PZeMZko2zHT+qrbTPT2mzCkbHpbXr9C8QTBy96sQnvOQJ6jzfrqUcI9lsbnUrY/QvJhw4tLMPXQDmzUxkojvGiNaNgxOd3e9RpD0Ik+1o5RA6+v8Vrt4k+IkGZoMobj6Fz8bmw9bojjtGEDrGfYt9vAhsECZDG0+Dde9TUbSVlm1wStj8odRw475UXOt9MCk24VuSAAK3D2sGo7Yrjev4rorDhsVguEN4bFbFsA1rCK4RygYjhdxoP8ALbzrjweTqy+LcwJnSNaQKY2g9oqpOL8/cqO4gpBhnMNbhr9TkfgvQ0wWU914Dxjm70tDW9c7oNAVK2KBY6lCR4xtHaq0RpJNAVGrkLIIAgGgvbpTR70pfouhmc+pPK60AwgQamyznCTubEmJVpgcqJAjQY7Wi5donVLQBmaVWpj+KqzMx0hTW3DO0TG40+Tt25GLxiK7RvGKJEcLVs5xIy6VT/Z0b5qJ5jvcvsd0JpzaD0gKm5orkOxdPqY9v3/pny593yJ+z43zUTzHJfs+N81E8x3uX2HBhNoOSOwIJiGARQDsCn1Me37/ANHLn3fIH7OjfNRPMd7k37PjfNv80r7Al2gnIZKSLCbQ8kdgUepj2/f+jlz7vnjgtnGy7Z6DMAwuMQWiE94LWF0PScjERSpx9y5+6XfXxHea73L69cwEUIBGwgEdib9gyn0aB9zD9yxtaLTteImHyFxd/wAh/mu9yXF3/If5rvcvrSJuPLVPg8D7mH+VTQNxJUi8tA+5h+5V6J6vkXi7/kP813uS0ET5D/Nd7l9cTG4sqKUloH3MP8qGDuNKk/FoH3MP8qdDq+SeLxPkP813uS4vE+Q/zXe5fXj9w5Wh8Ggfcw/cqv7Ilvo8D7mH+VOh1fJvF4nyH+a73LrHAvyYcxDNdJGLGMYQa0qcUQjU1oJJPNzrso3Dlfo0D7qH7k0OShQiRChw4daVwMa2vThF1O6o6rUe5tfoXPuGCCBJ6VxoGkClgTeoIrn+8P8AWuhy2RUe6Eux4GNjXUNsTWu9ISluG0SWjcaeRvOnGxpGE9jg7EC4gEHC5xxubbZi7KL1Qw7D2JpZoBAAAGwAAdgVwqtp3O0xGoNjG0dqrPaamxUauw8h0BQk+EKrFNylpnbfQpmQwRU5lA0vkmmLUTRHYTQWShcrO/65kAwTdWHixUcRgaKjNRtik2r6EEeIq40WQ6FuzvKhMUjX6EDRjcqSX1p2MBFTmhinDlZAUxl1qKEbhHCOI0N0b4YAqMwgkoFSxFHpjt9Cn0LdneUDwxYKGYzTOiEGgNgpIbQ4VN0Ay16o44shi8nK1UMNxcaG4QAw3HSreEKN0IAVAyUOmdt9CAXG6swRYJCENnpUT3lpoMkDzFiml7lFCGLO6UUYbiyA4osVWDlIx5JoclLoW7PSgPCFUebnpT6Z230KZsMEVIzQNxcbUJi0tsSSQOG47m2pIjBleqSSBhExWT6Gl65J0kA8YOxPoAb1zTpIBMTDbYnAx52okkgRbguL6kwi4rbUkkBcXG1Dxg7EkkDiDW+1MX4bZp0kCHLztRIsw3SSQNpq2pnbtRcXG1JJAOnOxOIeK+1JJAicFhdMDjsbJ0kCMPDfYm052JJIC4uNqHTUtTKySSD/2Q==");
        Product product2 = new Product(2, "Iphone", "180000", "abc",
                "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQA");

        cart.addToCart(product1, 2);
        cart.addToCart(product2, 1);

    }

//    public void navbarClick(){
//        // Xem thông tin tài khoản
//        iconAccount.setOnClickListener(view -> {
//            SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
//            String username = sharedPreferences.getString("username", null);
//
//            if(username == null) {
//                // Chưa đăng nhập
//                Intent intent = new Intent(HomeActivity.this, DangNhapActivity.class);
//                startActivity(intent);
//            } else {
//                // Đã đăng nhập
//                Intent intent = new Intent(HomeActivity.this, AccountActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        // Giỏ hàng
//        iconHome.setOnClickListener(view -> {
//            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
//            startActivity(intent);
//        });
//    }
}
