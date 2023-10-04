package com.jakaa.roomdatabase.fragments.update

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jakaa.roomdatabase.R
import com.jakaa.roomdatabase.data.viewmodel.UserViewModel
import com.jakaa.roomdatabase.databinding.FragmentUpdateBinding
import com.jakaa.roomdatabase.model.User


class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

       val updateFirstName = view.findViewById<EditText>(R.id.updateFirstName).setText(args.currentUser.firstName)
        view.findViewById<EditText>(R.id.updateLastName).setText(args.currentUser.lastName)
        view.findViewById<EditText>(R.id.updateAge).setText(args.currentUser.age)

        view.findViewById<Button>(R.id.update_btn).setOnClickListener {
            updateItem()
        }

        //Add menu
        setHasOptionsMenu(true)

        return view
    }
    private fun updateItem(){
        val firstName = binding.updateFirstName.text.toString()
        val lastName = binding.updateLastName.text.toString()
        val age = Integer.parseInt(binding.updateAge.text.toString())

        if (inputCheck(firstName, lastName, binding.updateAge.text)){
            //Create User Object
            val updateUser = User(args.currentUser.id, firstName, lastName, age)
            //Update Current User
            mUserViewModel.updateUser(updateUser)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
            //Navigate Back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean{
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete){
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
       val  builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_, _ ->
            mUserViewModel.deleteUser(args.currentUser)
            Toast.makeText(
                requireContext(), "Successfully removed: ${args.currentUser.firstName}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){_, _->
            builder.setTitle("Delete ${args.currentUser.firstName}?")
            builder.setMessage("Are you sure want to delete ${args.currentUser.firstName}")
            builder.create().show()
        }
    }


}