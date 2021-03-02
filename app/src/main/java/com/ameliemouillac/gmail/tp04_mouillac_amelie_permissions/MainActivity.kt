package com.ameliemouillac.gmail.tp04_mouillac_amelie_permissions

import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    val requestCodePermission = 0
    lateinit var permButton: Button
    lateinit var permText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permButton = findViewById(R.id.buttonPerm)
        permText = findViewById(R.id.welcoming_text)
        permText.text = "Bienvenue sur notre application, pour avoir votre position cliquer sur le bouton 'Autoriser l'accès'"

        permButton.setOnClickListener(
            View.OnClickListener {
                checkPermission()
            }
        )
    }

    fun checkPermission() {

        if (ContextCompat.checkSelfPermission(
                baseContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Verifier si l'utilisateur a déjà refusé la permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this as Activity,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // utilisateur a deja refusé la permission, popup d'explication
                permText.text = "Vous devez autoriser l'accès au GPS pour qu'on puisse récupérer votre position"
                displayDialog()
            } else {
                // Demander la permission
                ActivityCompat.requestPermissions(
                    this as Activity,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    requestCodePermission
                )
            }
        } else {
            // Continuer car la permission est accordée
            permText.text = "Bienvenue !"
        }
    }

    fun displayDialog() {
        val builder = AlertDialog.Builder(this@MainActivity)

        // Set the alert dialog title
        builder.setTitle("Autorisation")

        // Display a message on alert dialog
        builder.setMessage("Nous avons besoin de votre accord pour afficher votre position GPS")

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("J'accepte") { dialog, which ->
            // Do something when user press the positive button
            Toast.makeText(applicationContext, "Parfait !", Toast.LENGTH_SHORT).show()

            // Demander la permission
            ActivityCompat.requestPermissions(
                this as Activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                requestCodePermission
            )
        }
        // Display a negative button on alert dialog
        builder.setNegativeButton("Je refuse") { dialog, which ->
            Toast.makeText(applicationContext, "You are not agree.", Toast.LENGTH_SHORT).show()
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()
        // Display the alert dialog on app interface
        dialog.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            requestCodePermission -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission accordée
                    permText.text = "Bienvenue !"
                } else {
                    // Permission refusée
                    // Désactiver certaines fonctionalités
                    // Afficher un message d'erreur
                    permText.text = "Vous devez autoriser l'accès au GPS pour qu'on puisse récupérer votre position"
                }
            }
            else -> {
                // le code ne concerne pas la permission, on ignore
            }
        }
    }
}
