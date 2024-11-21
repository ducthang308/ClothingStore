package com.example.duanandroid.View;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.duanandroid.R;

import java.io.File;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSanphamActivity extends AppCompatActivity {
    private Map<String, Integer> categoryIds = new HashMap<>();
    private EditText edt_productname, product_color, product_price;
    private Spinner spinnerCategory;
    private LinearLayout imageContainer;
    private Button btnLuu;
    private String token;
    private ApiProduct apiProduct;
    private Uri[] selectedImageUris;
    private List<Category> categoryList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private APICaterogy categoriesApi;
    private static final int REQUEST_IMAGE_PICK = 1;
    private static final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 2;
    private int selectedCategoryIdForProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sanpham);

        imageContainer = findViewById(R.id.imageContainer);
        ImageView arrow_quanlisp = findViewById(R.id.arrow_quanlisp);

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

        apiProduct = APIClient.createProduct();
        apiProduct = APIClient.uploadImages();

        arrow_quanlisp.setOnClickListener(view -> {
            Intent intent = new Intent(AddSanphamActivity.this, ManageProductActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.imageView).setOnClickListener(view -> {
            checkAndRequestStoragePermission();
        });

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedCategoryName = parentView.getItemAtPosition(position).toString();
                Integer selectedCategoryId = categoryIds.get(selectedCategoryName);

                // Lưu trữ ID của danh mục đã chọn
                selectedCategoryIdForProduct = selectedCategoryId;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle case when nothing is selected
            }
        });

        btnLuu.setOnClickListener(view -> {
            if (validateInput()) {
                // Lấy danh mục đã chọn dựa trên vị trí chọn trong Spinner
                int selectedPosition = spinnerCategory.getSelectedItemPosition();

                // Kiểm tra xem vị trí đã chọn có hợp lệ không
                if (selectedPosition >= 0 && selectedPosition < categoryList.size()) {
                    Category selectedCategory = categoryList.get(selectedPosition);  // Lấy category từ danh sách

                    // Kiểm tra nếu ID danh mục hợp lệ
                    if (selectedCategory.getId() != 0) {  // Nếu ID không phải là 0 hoặc null
                        // Tạo ProductDTO với các thông tin của sản phẩm và ID danh mục
                        ProductDTO productDTO = new ProductDTO();
                        productDTO.setProductName(edt_productname.getText().toString().trim());
                        productDTO.setColor(product_color.getText().toString().trim());
                        productDTO.setPrice((float) Double.parseDouble(product_price.getText().toString().trim()));

                        // Thiết lập ID danh mục của sản phẩm
                        productDTO.setCategory(selectedCategory.getId());  // Gửi ID danh mục

                        // Gọi hàm để tạo sản phẩm
                        createProduct(productDTO);

                    }
                }
            }
        });

        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategory(selectedCategoryIdForProduct);

    }

    private void initViews() {
        edt_productname = findViewById(R.id.edt_productname);
        product_color = findViewById(R.id.product_color);
        product_price = findViewById(R.id.product_price);
        spinnerCategory = findViewById(R.id.spinner_category);
        btnLuu = findViewById(R.id.btn_AddProduct); // Thêm dòng này để khởi tạo btnLuu
        imageContainer = findViewById(R.id.imageContainer);
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

            // Gán lại mảng selectedImageUris với tất cả URI ảnh đã chọn
            selectedImageUris = imageUris.toArray(new Uri[0]);
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
        if (selectedImageUris == null || selectedImageUris.length == 0) {
            Toast.makeText(this, "Vui lòng chọn ít nhất một hình ảnh.", Toast.LENGTH_SHORT).show();
            return false;
        }
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
                if (response.isSuccessful()) {
                    Long productId = Long.valueOf(response.body()); // Lấy ID sản phẩm từ phản hồi
                    uploadImages(productId); // Gọi hàm tải lên ảnh với ID sản phẩm
                    Toast.makeText(AddSanphamActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();

                } else {
                    handleErrorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(AddSanphamActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImages(Long productId) {
        List<MultipartBody.Part> imageParts = prepareImageParts();

        if (imageParts.isEmpty()) {
            Toast.makeText(this, "Không có ảnh nào để tải lên.", Toast.LENGTH_SHORT).show();
            return;
        }

        apiProduct.uploadImages( productId, imageParts).enqueue(new Callback<List<ProductImageDTO>>() {
            @Override
            public void onResponse(Call<List<ProductImageDTO>> call, Response<List<ProductImageDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(AddSanphamActivity.this, "Tải lên ảnh thành công", Toast.LENGTH_SHORT).show();
                    navigateToProductManage();
                }
                else
                {
                    try {
                        String error = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
                        Log.e("UploadProductImages", "API Error: " + error);
                        Toast.makeText(AddSanphamActivity.this, "API Error: " + error, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(AddSanphamActivity.this, "Lỗi không xác định xảy ra", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ProductImageDTO>> call, Throwable t) {
                Log.e("UploadProductImages", "Network error: " + t.getMessage());
                Toast.makeText(AddSanphamActivity.this, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<MultipartBody.Part> prepareImageParts() {
        List<MultipartBody.Part> imageParts = new ArrayList<>();
        if (selectedImageUris != null) {
            for (Uri imageUri : selectedImageUris) {
                String realPath = getRealPathFromURI(imageUri);
                Log.d("PrepareImageParts", "Real path: " + realPath);

                if (realPath != null) {
                    File imageFile = new File(realPath);
                    if (imageFile.exists()) {
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
                        MultipartBody.Part part = MultipartBody.Part.createFormData("files", imageFile.getName(), requestBody);
                        imageParts.add(part);
                    } else {
                        Log.e("PrepareImageParts", "File does not exist: " + realPath);
                    }
                } else {
                    Log.e("PrepareImageParts", "Real path is null for URI: " + imageUri);
                }
            }
        }
        return imageParts;
    }

    private String getRealPathFromURI(Uri uri) {
        if (uri == null) return null;
        String[] projection = {MediaStore.Images.Media.DATA};
        try (Cursor cursor = getContentResolver().query(uri, projection, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                return cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void handleErrorResponse(Response<String> response) {
        try {
            String errorMessage = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
            Toast.makeText(AddSanphamActivity.this, "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("CreateProduct", "Error handling response: " + e.getMessage());
            Toast.makeText(AddSanphamActivity.this, "Lỗi không xác định xảy ra", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToProductManage() {
//        Intent intent = new Intent(AddSanphamActivity.this, ManageProductActivity.class);
//        //startActivity(intent);
//        finish();
//        // Kết thúc AddSanphamActivity để không quay lại khi nhấn nút back
    }
}