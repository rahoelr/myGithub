package com.example.mygithub.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.mygithub.databinding.ActivityThemeSettingBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ThemeSetting : AppCompatActivity() {

    private lateinit var binding: ActivityThemeSettingBinding
    private lateinit var settingViewModel: SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val switchTheme = binding.switchTheme

        val pref = SettingPreferences.getInstance(application.dataStore)
        settingViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
            settingViewModel.saveThemeSetting(isChecked)
            val pref = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
            val editor = pref.edit()
            editor.putBoolean("isDarkModeActive", isChecked)
            editor.apply()
        }

        runBlocking {
            val isDarkModeActive = pref.getThemeSetting().first()
            switchTheme.isChecked = isDarkModeActive
        }
    }
}
