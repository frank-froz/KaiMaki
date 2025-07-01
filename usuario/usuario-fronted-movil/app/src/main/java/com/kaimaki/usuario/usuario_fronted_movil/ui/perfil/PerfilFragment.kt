package com.kaimaki.usuario.usuario_fronted_movil.ui.perfil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.storage.FirebaseStorage
import com.kaimaki.usuario.usuario_fronted_movil.R
import com.kaimaki.usuario.usuario_fronted_movil.data.repository.UserRepositoryImpl

class PerfilFragment : Fragment() {

    private lateinit var viewModel: PerfilViewModel

    private lateinit var imgFotoPerfil: ShapeableImageView
    private lateinit var ivEditarFoto: ImageView
    private lateinit var tvCorreo: TextView
    private lateinit var etNombre: TextInputEditText
    private lateinit var etApellido: TextInputEditText
    private lateinit var etTelefono: TextInputEditText
    private lateinit var etPresentacion: TextInputEditText
    private lateinit var etDireccion: TextInputEditText
    private lateinit var etDistrito: TextInputEditText
    private lateinit var etProvincia: TextInputEditText
    private lateinit var etDepartamento: TextInputEditText
    private lateinit var btnGuardar: Button
    private lateinit var btnCancelar: Button

    private lateinit var pickImageLauncher: ActivityResultLauncher<String>
    private var fotoUrlSubida: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_perfil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userRepository = UserRepositoryImpl(requireContext())
        val factory = PerfilViewModelFactory(userRepository)
        viewModel = ViewModelProvider(this, factory)[PerfilViewModel::class.java]

        imgFotoPerfil = view.findViewById(R.id.imgFotoPerfil)
        ivEditarFoto = view.findViewById(R.id.ivEditarFoto)
        tvCorreo = view.findViewById(R.id.tvCorreo)
        etNombre = view.findViewById(R.id.etNombre)
        etApellido = view.findViewById(R.id.etApellido)
        etTelefono = view.findViewById(R.id.etTelefono)
        etPresentacion = view.findViewById(R.id.etPresentacion)
        etDireccion = view.findViewById(R.id.etDireccion)
        etDistrito = view.findViewById(R.id.etDistrito)
        etProvincia = view.findViewById(R.id.etProvincia)
        etDepartamento = view.findViewById(R.id.etDepartamento)
        btnGuardar = view.findViewById(R.id.btnGuardar)
        btnCancelar = view.findViewById(R.id.btnCancelar)

        pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                val storageRef = FirebaseStorage.getInstance().reference
                val fileRef = storageRef.child("fotos_perfil/${System.currentTimeMillis()}.jpg")

                fileRef.putFile(uri)
                    .addOnSuccessListener {
                        fileRef.downloadUrl.addOnSuccessListener { downloadUri ->
                            fotoUrlSubida = downloadUri.toString()

                            Glide.with(this)
                                .load(fotoUrlSubida)
                                .placeholder(R.drawable.default_avatar)
                                .into(imgFotoPerfil)

                            Toast.makeText(requireContext(), "Foto subida correctamente", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Error al subir imagen", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        ivEditarFoto.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        viewModel.usuario.observe(viewLifecycleOwner) { usuario ->
            usuario?.let {
                tvCorreo.text = it.correo ?: ""
                etNombre.setText(it.nombre)
                etApellido.setText(it.apellido)
                etTelefono.setText(it.telefono ?: "")
                etPresentacion.setText(it.presentacion ?: "")
                etDireccion.setText(it.direccion)
                etDistrito.setText(it.distrito)
                etProvincia.setText(it.provincia)
                etDepartamento.setText(it.departamento)

                Glide.with(this)
                    .load(it.fotoPerfil)
                    .placeholder(R.drawable.default_avatar)
                    .into(imgFotoPerfil)
            }
        }

        viewModel.mensaje.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        btnGuardar.setOnClickListener {
            val usuarioActual = viewModel.usuario.value
            if (usuarioActual != null) {
                val actualizado = usuarioActual.copy(
                    nombre = etNombre.text.toString(),
                    apellido = etApellido.text.toString(),
                    telefono = etTelefono.text.toString(),
                    presentacion = etPresentacion.text.toString(),
                    direccion = etDireccion.text.toString(),
                    distrito = etDistrito.text.toString(),
                    provincia = etProvincia.text.toString(),
                    departamento = etDepartamento.text.toString(),
                    fotoPerfil = fotoUrlSubida ?: usuarioActual.fotoPerfil
                )
                viewModel.actualizarPerfil(actualizado)
            }
        }

        btnCancelar.setOnClickListener {
            viewModel.cargarPerfil()
        }

        viewModel.cargarPerfil()
    }
}
