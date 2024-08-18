package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityShopItemBinding
import com.example.shoppinglist.domain.ShopItem.Companion.UNDEFINED_ID

class ShopItemActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private lateinit var binding: ActivityShopItemBinding

    //    private var viewModel = ShopItemViewModel()
//
    private var screenMode = MODE_UNKNOWN
    private var shopItemId = UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        Log.d("ShopItemActivity", "OnCreate")
        setContentView(binding.root)

        parseIntent()
        if (savedInstanceState == null) {
            launchRightMode()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("ShopItemActivity","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("ShopItemActivity","onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("ShopItemActivity","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("ShopItemActivity","onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ShopItemActivity","onDestroy")
    }

    override fun onEditingFinished() {
        Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
        finish()
    }
    private fun launchRightMode() {
        val fragment = when (screenMode) {
            EXTRA_MODE_ADD -> ShopItemFragment.newInstanceAddItem()
            EXTRA_MODE_EDIT -> ShopItemFragment.newInstanceEditItem(shopItemId)
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .commit()
    }

    fun parseIntent() {
        if (!intent.hasExtra(EXTRA_MODE)) {
            throw RuntimeException("MODE in Intent is absent")
        }
        val mode = intent.getStringExtra(EXTRA_MODE)
        if (mode != EXTRA_MODE_ADD && mode != EXTRA_MODE_EDIT) {
            throw RuntimeException("Wrong MODE specified: $mode")
        }
        screenMode = mode
        if (screenMode == EXTRA_MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Id wasn't specified")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, UNDEFINED_ID)
        }
    }


    //
    companion object {
        private const val EXTRA_MODE = "extra_mode"
        private const val EXTRA_MODE_ADD = "mode_add"
        private const val EXTRA_MODE_EDIT = "mode_edit"
        private const val EXTRA_SHOP_ITEM_ID = "shop_item_id"
        private const val MODE_UNKNOWN = ""
        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, EXTRA_MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, id: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, EXTRA_MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, id)
            return intent
        }
    }
}