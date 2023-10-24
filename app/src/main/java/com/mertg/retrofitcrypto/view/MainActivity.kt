package com.mertg.retrofitcrypto.view

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mertg.retrofitcrypto.R
import com.mertg.retrofitcrypto.adapter.RecyclerViewAdapter
import com.mertg.retrofitcrypto.databinding.ActivityMainBinding
import com.mertg.retrofitcrypto.model.CryptoModel
import com.mertg.retrofitcrypto.service.CryptoAPI
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Listener {
    private lateinit var binding : ActivityMainBinding
//    private val BASE_URL = "https://raw.githubusercontent.com/"
    private val BASE_URL = "https://api.coingecko.com/api/v3/"
    private var cryptoModels : ArrayList<CryptoModel>? = null

    private var recyclerViewAdapter : RecyclerViewAdapter? = null

    //Disposable
    private var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.darkThemeColor)))

        compositeDisposable = CompositeDisposable()

        //RecyclerView
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        loadData()
    }

    private fun loadData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoAPI::class.java)
        //RXJava ile kullanımı

        compositeDisposable?.add(retrofit.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse))

        //CryptoAPI interface'indeki getData() fonksiyonu ile kullanılan call.enqueue kullanımı.
        /*
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val call = service.getData()
        val service = retrofit.create(CryptoAPI::class.java)

        call.enqueue(object : Callback<List<CryptoModel>>{
            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        cryptoModels = ArrayList(it)

                        cryptoModels?.let {
                            recyclerViewAdapter = RecyclerViewAdapter(it,this@MainActivity)
                            binding.recyclerView.adapter = recyclerViewAdapter
                        }

                    }
                }
            }
            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })*/
    }

    private fun handleResponse(cryptoList: List<CryptoModel>) {
        try {
            cryptoModels = ArrayList(cryptoList)
            cryptoModels?.let {
                // Verileri isim (name) özelliğine göre alfabetik sırala
                it.sortBy { it.name }
                recyclerViewAdapter = RecyclerViewAdapter(it, this@MainActivity)
                binding.recyclerView.adapter = recyclerViewAdapter
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Hata durumunda burada uygun işlemleri yapabilirsiniz.
        }
    }



    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this, "Clicked: ${cryptoModel.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh_button -> {
                loadData()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


}