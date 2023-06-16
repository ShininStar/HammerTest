package com.example.hammertest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.hammertest.R
import com.example.hammertest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/*Добрый день! Старался сделать код оптимальным, но я начинающий разработчик, поэтому понимаю, что
* возможно не все мои решения являются лучшими) Буду рад, если моя работа подойдет, я готов работать
* и развиваться! Если же нет, то буду благдарен любой обратной связи, чтобы не допускать ошибок
* в будущем. Спасибо!*/
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //подключаем кнопки через binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //устанавливаем стартовый фрагмент
        replaceFragment(MenuFragment())
        bottomNavPress()
    }

    //следим на что нажали в меню внизу, реализовал только один экран
    private fun bottomNavPress() {
        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu_item -> {
                    replaceFragment(MenuFragment())
                }
                else -> {}
            }
            true
        }
    }

    //функция для перемещения между фрагментами
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentHolder, fragment)
            .commit()
    }
}