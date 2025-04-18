package com.bvc.ordering.view.dialog

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.bvc.domain.log
import com.bvc.domain.model.OptionsEntity
import com.bvc.domain.model.ProductEntity
import com.bvc.ordering.R
import com.bvc.ordering.databinding.DialogOptionSelectBinding

class OptionSelectDialog(
    private val product: ProductEntity,
    private val onOptionSelected: (ProductEntity) -> Unit,
) : DialogFragment() {
    private var _binding: DialogOptionSelectBinding? = null
    private val binding get() = _binding!!

    private val selectedOptions = mutableMapOf<String, MutableList<String>>()
    private val radioButtonMap = mutableMapOf<String, List<RadioButton>>()
    val radioButtons = mutableListOf<RadioButton>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.TransparentDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DialogOptionSelectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupOptions()
        binding.btnAddToCart.setOnClickListener {
            val requiredGroups = product.optionGroups.filter { it.required }

            val allRequiredSelected =
                requiredGroups.all { group ->
                    selectedOptions[group.optionGroupId]?.isNotEmpty() == true
                }

            if (!allRequiredSelected) {
                Toast.makeText(requireContext(), "모든 필수 옵션을 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val cartEntity = createProductEntity()
            onOptionSelected(cartEntity)
            dismiss()
        }
        binding.btnClose.setOnClickListener { dismiss() }
        binding.tvAddToCart.text = "${product.basePrice}원 담기"
        binding.tvTitle.text = product.name
    }

    private fun setupOptions() {
        product.optionGroups.forEach { option ->
            val optionGroup =
                LinearLayout(requireContext()).apply {
                    orientation = LinearLayout.VERTICAL
                    setPadding(0, 8, 0, 8)
                }

            val title =
                TextView(requireContext()).apply {
                    text = option.name
                    setTypeface(null, Typeface.BOLD)
                    setTextColor(context.getColor(R.color.bvc_8E9393))
                }
            optionGroup.addView(title)

            if (option.required) {
                // 필수 선택 (RadioButton)
                val group =
                    RadioGroup(requireContext()).apply {
                        orientation = LinearLayout.VERTICAL
                    }

                // 🔥 여기서 그룹별로 라디오 버튼 리스트 만들기
                val radioList = mutableListOf<RadioButton>()

                option.options.forEach { opt ->
                    val itemLayout =
                        LinearLayout(requireContext()).apply {
                            orientation = LinearLayout.HORIZONTAL
                            layoutParams =
                                LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                )
                            setPadding(0, 8, 0, 8)
                        }

                    val radioButton =
                        RadioButton(requireContext()).apply {
                            text = opt.name
                            setTextColor(context.getColor(R.color.bvc_8E9393))
                            setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, 12f)
                            isChecked = opt.isSelected
                            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                            setOnClickListener {
                                updateSelection(option.optionGroupId, opt.productOptionsId, isMultiple = false)
                            }
                        }

                    radioList.add(radioButton) // 💡 그룹별 리스트에 추가

                    val priceView =
                        TextView(requireContext()).apply {
                            text = "+${opt.price}원"
                            setTextColor(context.getColor(R.color.bvc_666E89))
                            setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, 16f)
                            setTypeface(null, Typeface.BOLD)
                            layoutParams =
                                LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                )
                        }

                    itemLayout.addView(radioButton)
                    itemLayout.addView(priceView)
                    group.addView(itemLayout)
                }

                // 여기서 그룹별 라디오버튼 리스트 저장
                radioButtonMap[option.optionGroupId] = radioList

                optionGroup.addView(group)
            } else {
                // 옵션 선택 (CheckBox)
                option.options.forEach { opt ->
                    val itemLayout =
                        LinearLayout(requireContext()).apply {
                            orientation = LinearLayout.HORIZONTAL
                            layoutParams =
                                LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                )
                            setPadding(0, 8, 0, 8)
                        }

                    val checkBox =
                        CheckBox(requireContext()).apply {
                            text = opt.name
                            isChecked = opt.isSelected
                            setTextColor(context.getColor(R.color.bvc_8E9393))
                            setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, 12f)
                            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                            setOnCheckedChangeListener { _, isChecked ->
                                updateSelection(option.optionGroupId, opt.productOptionsId, isMultiple = true, isChecked)
                            }
                        }

                    val priceView =
                        TextView(requireContext()).apply {
                            text = "+${opt.price}원"
                            setTextColor(context.getColor(R.color.bvc_666E89))
                            setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, 16f)
                            setTypeface(null, Typeface.BOLD)
                            layoutParams =
                                LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                )
                        }

                    itemLayout.addView(checkBox)
                    itemLayout.addView(priceView)
                    optionGroup.addView(itemLayout)
                }
            }

            binding.optionContainer.addView(optionGroup)
        }
    }

    private fun updateSelection(
        optionId: String,
        selectedId: String,
        isMultiple: Boolean,
        isChecked: Boolean = true,
    ) {
        val selectedList = selectedOptions.getOrDefault(optionId, mutableListOf())

        if (isMultiple) {
            if (isChecked) {
                if (!selectedList.contains(selectedId)) {
                    selectedList.add(selectedId)
                }
            } else {
                selectedList.remove(selectedId)
            }
        } else {
            // 기존 선택된 RadioButton들 모두 해제
            radioButtonMap[optionId]?.forEach { it.isChecked = false }

            selectedList.clear()
            selectedList.add(selectedId)

            // 새로 선택한 버튼만 체크
            val target = radioButtonMap[optionId]?.find { it.text.startsWith(getOptionName(optionId, selectedId)) }
            target?.isChecked = true
        }

        selectedOptions[optionId] = selectedList
        updateTotalPrice()
    }

    private fun getOptionName(
        optionId: String,
        selectedId: String,
    ): String =
        product.optionGroups
            .find { it.optionGroupId == optionId }
            ?.options
            ?.find { it.productOptionsId == selectedId }
            ?.name ?: ""

//    private fun updateTotalPrice() {
//        var totalPrice = product.basePrice.toInt()
//
//        selectedOptions.forEach { (optionId, selectedIds) ->
//            val option = product.optionGroups.find { it.optionGroupId == optionId }
//            selectedIds.forEach { id ->
//                val selectedOption = option?.options?.find { it.productOptionsId == id }
//                totalPrice += selectedOption?.price?.toInt() ?: 0
//            }
//        }
//
//        binding.tvAddToCart.text = "${totalPrice}원 담기"
//    }

    private fun updateTotalPrice() {
        var totalPrice = product.basePrice.toInt()

        selectedOptions.forEach { (optionId, selectedIds) ->
            val option = product.optionGroups.find { it.optionGroupId == optionId }
            selectedIds.forEach { id ->
                val selectedOption = option?.options?.find { it.productOptionsId == id }
                totalPrice += selectedOption?.price?.toInt() ?: 0
            }
        }

        binding.tvAddToCart.text = "${totalPrice}원 담기"

        // 🔽 필수 옵션이 모두 선택되었는지 체크
        val requiredOptions = product.optionGroups.filter { it.required }
        val isAllRequiredSelected =
            requiredOptions.all {
                selectedOptions[it.optionGroupId]?.isNotEmpty() == true
            }
        log.e("isAllRequiredSelected: $isAllRequiredSelected")

//        binding.btnAddToCart.isEnabled = isAllRequiredSelected

        // 선택 안 했을 때 버튼 색상 흐리게
        binding.btnAddToCart.alpha = if (isAllRequiredSelected) 1.0f else 0.5f
    }

    private fun createProductEntity(): ProductEntity {
        val updatedOptions =
            product.optionGroups.map { option ->
                option.copy(
                    options =
                        option.options.map { opt ->
                            opt.copy(isSelected = selectedOptions[option.optionGroupId]?.contains(opt.productOptionsId) == true)
                        } as ArrayList<OptionsEntity>,
                )
            }

        val updatedProduct = product.copy(optionGroups = updatedOptions)
        return updatedProduct
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
