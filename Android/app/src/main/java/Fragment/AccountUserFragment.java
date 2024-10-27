package Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.duanandroid.R;
import com.example.duanandroid.View.CartActivity;
import com.example.duanandroid.View.HelpActivity;
import com.example.duanandroid.View.KhoVoucherActivity;
import com.example.duanandroid.View.changePassActivity;
import com.example.duanandroid.View.chatUserActivity;
import com.example.duanandroid.View.editAccountActivity;

public class AccountUserFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_user, container, false);

        // Find views and set onClickListeners
        TextView btn = view.findViewById(R.id.view_all_oders);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TabLayOutActivity.class);
                intent.putExtra("tabPosition", 3);
                startActivity(intent);
            }
        });

        LinearLayout btn1 = view.findViewById(R.id.waiting_payment_icon);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TabLayOutActivity.class);
                intent.putExtra("tabPosition", 0);
                startActivity(intent);
            }
        });

        LinearLayout btn2 = view.findViewById(R.id.waiting_shipping_icon);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TabLayOutActivity.class);
                intent.putExtra("tabPosition", 1);
                startActivity(intent);
            }
        });

        LinearLayout btn3 = view.findViewById(R.id.waiting_delivery_icon);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TabLayOutActivity.class);
                intent.putExtra("tabPosition", 2);
                startActivity(intent);
            }
        });

        LinearLayout btn4 = view.findViewById(R.id.review_icon);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TabLayOutActivity.class);
                intent.putExtra("tabPosition", 3);
                startActivity(intent);
            }
        });



        RelativeLayout support = view.findViewById(R.id.support);
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout changepass = view.findViewById(R.id.changepassword);
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), changePassActivity.class);
                startActivity(intent);
            }
        });

        ImageView user = view.findViewById(R.id.avatarImage);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), editAccountActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout chatwithshop = view.findViewById(R.id.Chat_with_shop);
        chatwithshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), chatUserActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout myreview = view.findViewById(R.id.My_review);
        myreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TabLayOutActivity.class);
                intent.putExtra("tabPosition", 3);
                startActivity(intent);
            }
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RelativeLayout khoVoucher = view.findViewById(R.id.khovoucher);
        khoVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), KhoVoucherActivity.class);
                startActivity(intent);
            }
        });
        RelativeLayout myshoppingcart = view.findViewById(R.id.My_shopping_cart);
        myshoppingcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
                intent.putExtra("origin", "CartToAccount");
                startActivity(intent);
            }
        });

        return view;
    }
}
