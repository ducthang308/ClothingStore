package com.example.duanandroid.View;

import static Interface.APIClient.uploadImages;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.duanandroid.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DTO.ProductDTO;
import DTO.ProductImageDTO;
import Interface.APICaterogy;
import Interface.APIClient;
import Interface.ApiProduct;
import Interface.PreferenceManager;
import Model.Category;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSanphamActivity extends AppCompatActivity {
    private Map<String, Integer> categoryIds = new HashMap<>();
    private EditText edt_productname, product_color, product_price;
    private ImageView imageView;
    private Spinner spinnerCategory;
    private LinearLayout imageContainer;
    private Button btnLuu;
    private String token;
    private ApiProduct apiProduct;
    private List<Uri> selectedImageUris = new ArrayList<>();
    private List<Category> categoryList = new ArrayList<>();
    private List<String> imageUrls = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private APICaterogy categoriesApi;
    private static final int REQUEST_IMAGE_PICK = 1;
    private static final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 2;
    private int selectedCategoryIdForProduct;
    private int productId = -1;
    private ImageView arrow_quanlisp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sanpham);

        String action = getIntent().getStringExtra("action");

        imageContainer = findViewById(R.id.imageContainer);
        arrow_quanlisp = findViewById(R.id.arrow_quanlisp);

        initViews();
        setupCategorySpinner();
        loadCategoryData();

        PreferenceManager preferenceManager = new PreferenceManager(this);
        token = preferenceManager.getToken();

        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "Token không hợp lệ. Vui lòng đăng nhập lại.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Xác định title
        TextView titleTextView = findViewById(R.id.title); // Thay R.id.title bằng ID của TextView tiêu đề trong layout
        if ("add".equals(action)) {
            titleTextView.setText("Thêm sản phẩm");
        } else if ("edit".equals(action)) {
            titleTextView.setText("Sửa sản phẩm");

            // Load thông tin sản phẩm để sửa nếu cần
            int productId = getIntent().getIntExtra("productId", -1);
            if (productId != -1) {
                // Thực hiện xử lý để tải dữ liệu sản phẩm và hiển thị
            }
        }

        apiProduct = APIClient.createProduct();

        arrow_quanlisp.setOnClickListener(view -> {
            Intent intent = new Intent(AddSanphamActivity.this, ManageProductActivity.class);
            startActivity(intent);
        });
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedCategoryName = parentView.getItemAtPosition(position).toString();
                Integer selectedCategoryId = categoryIds.get(selectedCategoryName);
                selectedCategoryIdForProduct = selectedCategoryId;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("productId")) {
            productId = intent.getIntExtra("productId", -1);
            String productName = intent.getStringExtra("productName");
            int productCategory = intent.getIntExtra("productCategory", -1);
            String productColor = intent.getStringExtra("productColor");
            float productPrice = intent.getFloatExtra("productPrice", 0);
            ArrayList<String> imageUrls = intent.getStringArrayListExtra("productImage");

            edt_productname.setText(productName);
            if (productCategory != -1) {
                for (int i = 0; i < spinnerCategory.getCount(); i++) {
                    if ((int) spinnerCategory.getItemAtPosition(i) == productCategory) {
                        spinnerCategory.setSelection(i);
                        break;
                    }
                }
            }
            product_color.setText(productColor);
            product_price.setText(String.valueOf(productPrice));
            if (imageUrls != null && !imageUrls.isEmpty()) {
                for (int i = 0; i < imageUrls.size() && i < imageContainer.getChildCount(); i++) {
                    String imageUrl = imageUrls.get(i);
                    ImageView imageView = (ImageView) imageContainer.getChildAt(i);
                    loadImageIntoImageView(imageUrl, imageView);
                }
            }
        }

        btnLuu.setOnClickListener(view -> {
            if (validateInput()) {
                int selectedPosition = spinnerCategory.getSelectedItemPosition();
                if (selectedPosition >= 0 && selectedPosition < categoryList.size()) {
                    Category selectedCategory = categoryList.get(selectedPosition);

                    if (selectedCategory.getId() != 0) {
                        ProductDTO productDTO = new ProductDTO();
                        productDTO.setProductName(edt_productname.getText().toString().trim());
                        productDTO.setColor(product_color.getText().toString().trim());
                        productDTO.setPrice((float) Double.parseDouble(product_price.getText().toString().trim()));
                        productDTO.setCategoryId(selectedCategory.getId());

                        if (productId == -1) {
                            createProduct(productDTO);
                        } else {
                            updateProduct(productId, productDTO);
                        }
                    }
                }
            }
        });
        imageView.setOnClickListener(view -> checkAndRequestStoragePermission());


    }

    private void loadImageIntoImageView(String imageUrl, ImageView imageView) {
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.co4la)
                .error(R.drawable.error)
                .into(imageView);
    }
    private void initViews() {
        edt_productname = findViewById(R.id.edt_productname);
        product_color = findViewById(R.id.product_color);
        product_price = findViewById(R.id.product_price);
        spinnerCategory = findViewById(R.id.spinner_category);
        btnLuu = findViewById(R.id.btn_AddProduct); // Thêm dòng này để khởi tạo btnLuu
        imageContainer = findViewById(R.id.imageContainer);
        imageView = findViewById(R.id.imageView);

    }
    private void setupCategorySpinner() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }
    private void checkAndRequestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!android.os.Environment.isExternalStorageManager()) {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);
            } else {
                openImagePicker();
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);
            } else {
                openImagePicker();
            }
        }
    }
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // Cho phép chọn nhiều ảnh
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            List<Uri> imageUris = new ArrayList<>(); // Danh sách chứa URI ảnh đã chọn

            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();

                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    imageUris.add(imageUri); // Thêm URI vào danh sách
                    if (i < imageContainer.getChildCount()) {
                        ImageView imageView = (ImageView) imageContainer.getChildAt(i);
                        imageView.setImageURI(imageUri); // Hiển thị ảnh trong ImageView
                    }
                }
            } else if (data.getData() != null) {
                Uri imageUri = data.getData();
                imageUris.add(imageUri); // Thêm URI vào danh sách
                if (imageContainer.getChildCount() > 0) {
                    ImageView imageView = (ImageView) imageContainer.getChildAt(0);
                    imageView.setImageURI(imageUri); // Hiển thị ảnh trong ImageView
                }
            }
            selectedImageUris = imageUris;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker();
            } else {
                Toast.makeText(this, "Quyền truy cập bị từ chối!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void loadCategoryData() {
        categoriesApi = APIClient.getcategory();
        Call<List<Category>> call = categoriesApi.getAllCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categoryList = response.body();
                    List<String> categoryNames = new ArrayList<>();
                    for (Category category : categoryList) {
                        categoryNames.add(category.getCategoryName());
                        categoryIds.put(category.getCategoryName(), category.getId());
                    }
                    adapter.clear();
                    adapter.addAll(categoryNames);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(AddSanphamActivity.this, "Không thể tải danh mục", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e("LoadCategoryData", "Error: " + t.getMessage());
                Toast.makeText(AddSanphamActivity.this, "Lỗi tải danh mục", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean validateInput() {
        if (edt_productname.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên sản phẩm.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (product_color.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập màu sắc sản phẩm.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (product_price.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập giá sản phẩm.", Toast.LENGTH_SHORT).show();
            return false;
        }
//        if (selectedImageUris == null || selectedImageUris.size() == 0) {
//            Toast.makeText(this, "Vui lòng chọn ít nhất một hình ảnh.", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        if (selectedCategoryIdForProduct == 0) { // Kiểm tra nếu danh mục chưa được chọn
            Toast.makeText(this, "Vui lòng chọn danh mục.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void createProduct(ProductDTO product) {
        apiProduct.createProduct("Bearer " + token, product).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        Long productId = Long.valueOf(response.body());
                        Log.d("CreateProduct", "ID sản phẩm: " + productId);
                        uploadImagesToServer(productId); // Gọi hàm upload ảnh
                        navigateToProductManage();
                    } catch (NumberFormatException e) {
                        Log.e("CreateProduct", "Phản hồi không phải ID hợp lệ: " + response.body(), e);
                        Toast.makeText(AddSanphamActivity.this, "Lỗi khi lấy ID sản phẩm.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    handleErrorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("CreateProduct", "Lỗi kết nối: " + t.getMessage());
                Toast.makeText(AddSanphamActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showToast(String message) {
        Toast.makeText(AddSanphamActivity.this, message, Toast.LENGTH_SHORT).show();
    }
    private void uploadImagesToServer(Long productId) {
        if (productId == null || productId <= 0) {
            Log.e("UploadImages", "ID sản phẩm không hợp lệ: " + productId);
            showToast("ID sản phẩm không hợp lệ.");
            return;
        }
        // Chuẩn bị danh sách MultipartBody.Part[]
        MultipartBody.Part[] imageParts = prepareImagePartsArray();
        if (imageParts.length == 0) {
            Log.e("UploadImages", "Không có hình ảnh nào được chuẩn bị.");
            showToast("Không có ảnh nào để tải lên.");
            return;
        }
        Log.d("UploadImages", "Số ảnh chuẩn bị tải lên: " + imageParts.length);

        apiProduct.uploadImages("Bearer " + token, productId, imageParts).enqueue(new Callback<List<ProductImageDTO>>() {
            @Override
            public void onResponse(Call<List<ProductImageDTO>> call, Response<List<ProductImageDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("UploadImages", "Tải ảnh thành công, số lượng: " + response.body().size());
                    showToast("Tải ảnh lên thành công!");
                } else {
                    handleErrorResponse(response);
                }
            }
            @Override
            public void onFailure(Call<List<ProductImageDTO>> call, Throwable t) {
                Log.e("UploadImages", "Lỗi kết nối khi tải ảnh: " + t.getMessage());
                showToast("Lỗi kết nối khi tải ảnh: " + t.getMessage());
            }
        });
    }
    private MultipartBody.Part[] prepareImagePartsArray() {
        List<MultipartBody.Part> imageParts = new ArrayList<>();
        if (selectedImageUris != null && !selectedImageUris.isEmpty()) {
            for (Uri imageUri : selectedImageUris) {
                String realPath = getRealPathFromURI(imageUri);
                if (realPath != null) {
                    File imageFile = new File(realPath);
                    if (imageFile.exists()) {
                        Log.d("PrepareImageParts", "Đang xử lý tệp: " + imageFile.getName());
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
                        MultipartBody.Part part = MultipartBody.Part.createFormData("files", imageFile.getName(), requestBody);
                        imageParts.add(part);
                    } else {
                        Log.e("PrepareImageParts", "Tệp không tồn tại: " + realPath);
                    }
                } else {
                    Log.e("PrepareImageParts", "Không thể lấy đường dẫn thực cho URI: " + imageUri);
                }
            }
        }

        return imageParts.toArray(new MultipartBody.Part[0]); // Trả về mảng
    }
    private String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        try (Cursor cursor = getContentResolver().query(uri, projection, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                return cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            Log.e("getRealPathFromURI", "Lỗi khi lấy đường dẫn thực từ URI", e);
        }
        return null;
    }
    private void handleErrorResponse(Response<?> response) {
        try {
            String errorMessage = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
            Log.e("ResponseError", "Phản hồi lỗi: " + errorMessage);
            showToast("Lỗi: " + errorMessage);
        } catch (Exception e) {
            Log.e("ResponseError", "Error reading response body", e);
            showToast("Lỗi không xác định xảy ra.");
        }
    }
    private void updateProduct(int productId, ProductDTO product) {
        apiProduct.updateProduct("Bearer " + token, productId, product).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddSanphamActivity.this, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    navigateToProductManage();
                } else {
                    handleErrorResponseUpdate(response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddSanphamActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("UpdateProduct", "Failed: " + t.getMessage());
            }
        });
    }

    private void handleErrorResponseUpdate(Response<ResponseBody> response) {
        try {
            int statusCode = response.code();
            String errorMessage = "Unknown error";
            if (response.errorBody() != null) {
                errorMessage = response.errorBody().string();
            }
            if (statusCode == 400) {
                Toast.makeText(AddSanphamActivity.this, "Dữ liệu không hợp lệ: " + errorMessage, Toast.LENGTH_SHORT).show();
            } else if (statusCode == 401) {
                Toast.makeText(AddSanphamActivity.this, "Token không hợp lệ, vui lòng đăng nhập lại.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddSanphamActivity.this, "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("UpdateProduct", "Error handling response: " + e.getMessage());
            Toast.makeText(AddSanphamActivity.this, "Lỗi không xác định xảy ra", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToProductManage() {
        startActivity(new Intent(AddSanphamActivity.this, ManageProductActivity.class));
        finish();
    }
}