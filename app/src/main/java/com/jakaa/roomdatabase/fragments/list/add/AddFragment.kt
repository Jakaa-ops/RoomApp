package com.jakaa.roomdatabase.fragments.list.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jakaa.roomdatabase.R
import com.jakaa.roomdatabase.model.User
import com.jakaa.roomdatabase.data.viewmodel.UserViewModel
import com.jakaa.roomdatabase.databinding.FragmentAddBinding


class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var mUserViewModel: UserViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mUserViewModel  = ViewModelProvider(this).get(UserViewModel::class.java)


        //val add_btn = view.findViewById<Button>(R.id.addId)
        binding.addButton.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {
        val firstName = binding.addFirstName.text.toString()
        val lastName = binding.AddLastName.text.toString()
        val age = binding.addAge.text

        if (inputCheck(firstName, lastName, age)){
            //Check User Object
            val user = User(0, firstName, lastName, Integer.parseInt(age.toString()))
            //Add Data to Database
            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully add", Toast.LENGTH_LONG).show()
            // Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill out the all fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean{
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }


}