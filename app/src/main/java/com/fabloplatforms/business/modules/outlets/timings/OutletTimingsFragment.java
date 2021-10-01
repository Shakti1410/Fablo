package com.fabloplatforms.business.modules.outlets.timings;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.fabloplatforms.business.R;
import com.fabloplatforms.business.databinding.FragmentOutletTimingsBinding;

import org.jetbrains.annotations.NotNull;

public class OutletTimingsFragment extends Fragment {

    private FragmentOutletTimingsBinding binding;
    private Context context;

    public OutletTimingsFragment() {
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOutletTimingsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        context = getContext();
        initView();
        return view;
    }

    private void initView() {

        // monday

        binding.switchMonday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.switchMonday24.setVisibility(View.VISIBLE);
                    binding.viewMonday.setVisibility(View.VISIBLE);
                    binding.switchMonday24.setChecked(true);
                } else {
                    binding.switchMonday24.setVisibility(View.GONE);
                    binding.viewMonday.setVisibility(View.GONE);
                    binding.lvMonday.setVisibility(View.GONE);
                }
            }
        });

        //monday

        binding.switchMonday24.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.lvMonday.setVisibility(View.GONE);
                } else {
                    binding.lvMonday.setVisibility(View.VISIBLE);
                }
            }
        });

        // tuesday

        binding.switchTuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.switchTuesday24.setVisibility(View.VISIBLE);
                    binding.viewTuesday.setVisibility(View.VISIBLE);
                    binding.switchTuesday24.setChecked(true);
                } else {
                    binding.switchTuesday24.setVisibility(View.GONE);
                    binding.viewTuesday.setVisibility(View.GONE);
                    binding.lvTuesday.setVisibility(View.GONE);
                }
            }
        });

        //tuesday

        binding.switchTuesday24.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.lvTuesday.setVisibility(View.GONE);
                } else {
                    binding.lvTuesday.setVisibility(View.VISIBLE);
                }
            }
        });

        // wednesday

        binding.switchWednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.switchWednesday24.setVisibility(View.VISIBLE);
                    binding.viewWednesday.setVisibility(View.VISIBLE);
                    binding.switchWednesday24.setChecked(true);
                } else {
                    binding.switchWednesday24.setVisibility(View.GONE);
                    binding.viewWednesday.setVisibility(View.GONE);
                    binding.lvWednesday.setVisibility(View.GONE);
                }
            }
        });

        //wednesday

        binding.switchWednesday24.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.lvWednesday.setVisibility(View.GONE);
                } else {
                    binding.lvWednesday.setVisibility(View.VISIBLE);
                }
            }
        });

        // thursday

        binding.switchThursday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.switchThursday24.setVisibility(View.VISIBLE);
                    binding.viewThursday.setVisibility(View.VISIBLE);
                    binding.switchThursday24.setChecked(true);
                } else {
                    binding.switchThursday24.setVisibility(View.GONE);
                    binding.viewThursday.setVisibility(View.GONE);
                    binding.lvThursday.setVisibility(View.GONE);
                }
            }
        });

        //thursday

        binding.switchThursday24.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.lvThursday.setVisibility(View.GONE);
                } else {
                    binding.lvThursday.setVisibility(View.VISIBLE);
                }
            }
        });

        // friday

        binding.switchFriday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.switchFriday24.setVisibility(View.VISIBLE);
                    binding.viewFriday.setVisibility(View.VISIBLE);
                    binding.switchFriday24.setChecked(true);
                } else {
                    binding.switchFriday24.setVisibility(View.GONE);
                    binding.viewFriday.setVisibility(View.GONE);
                    binding.lvFriday.setVisibility(View.GONE);
                }
            }
        });

        //friday

        binding.switchFriday24.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.lvFriday.setVisibility(View.GONE);
                } else {
                    binding.lvFriday.setVisibility(View.VISIBLE);
                }
            }
        });

        // saturday

        binding.switchSaturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.switchSaturday24.setVisibility(View.VISIBLE);
                    binding.viewSaturday.setVisibility(View.VISIBLE);
                    binding.switchSaturday24.setChecked(true);
                } else {
                    binding.switchSaturday24.setVisibility(View.GONE);
                    binding.viewSaturday.setVisibility(View.GONE);
                    binding.lvSaturday.setVisibility(View.GONE);
                }
            }
        });

        // saturday

        binding.switchSaturday24.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.lvSaturday.setVisibility(View.GONE);
                } else {
                    binding.lvSaturday.setVisibility(View.VISIBLE);
                }
            }
        });

        // sunday

        binding.switchSunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.switchSunday24.setVisibility(View.VISIBLE);
                    binding.viewSunday.setVisibility(View.VISIBLE);
                    binding.switchSunday24.setChecked(true);
                } else {
                    binding.switchSunday24.setVisibility(View.GONE);
                    binding.viewSunday.setVisibility(View.GONE);
                    binding.lvSunday.setVisibility(View.GONE);
                }
            }
        });

        // sunday

        binding.switchSunday24.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.lvSunday.setVisibility(View.GONE);
                } else {
                    binding.lvSunday.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}