package com.sebastian.osorios.udea.atlas.Util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import com.sebastian.osorios.udea.atlas.R
import java.net.*
import java.io.*

class CommonFunctions {

    fun getErrorMessage(codigo : String,body:String): String{
        when(codigo){
            "301"->{
                return "El recurso no fue modificado. Puedes usar la versión en caché."
            }
            "400"->{
                return "Solicitud incorrecta."
            }
            "401"->{
                return "Solicitud incorrecta."
            }
            "402"->{
                return "Verifique su conexion a internet."
            }
            "403"->{
                return "El usuario ingresado, no se encuentra registrado."
            }
            "404"->{
                return "El recurso solicitado no existe."
            }
            "405"->{
                return "Verifique los datos ingresados."
            }
            "406"->{
                return "No ha ingresado los datos correctamente, porfavor verifique e intente nuevamente."
            }
            "422"->{
                if(body.equals("user must have atleast 11 years to have an account")){
                    return "el usuario debe tener al menos 11 años para tener una cuenta."
                }else if(body.substring(0,6).equals("Email ")){
                    return "El correo electronico ingresado ya se encuentra registrado"
                }else{
                    return "Error en los datos ingresados."
                }

            }
            "429"->{
                return "Demasiadas solicitudes. La solicitud fue rechazada debido a la limitación de la tasa."
            }
            "500"->{
                return "Error interno en el servidor."
            }
            else->{
                return "Error inesperado en la consulta."
            }
        }
    }

    fun getFlagsOfCountry(spelling : String): Int {
        when(spelling){
            "ad"->{
                return R.drawable.drawable_grupo2_ad
            }
            "ae"->{
                return R.drawable.drawable_grupo2_ae
            }
            "af"->{
                return R.drawable.drawable_grupo2_af
            }
            "ag"->{
                return R.drawable.drawable_grupo2_ag
            }
            "ai"->{
                return R.drawable.drawable_grupo2_ai
            }
            "al"->{
                return R.drawable.drawable_grupo2_al
            }
            "am"->{
                return R.drawable.drawable_grupo2_am
            }
            "ao"->{
                return R.drawable.drawable_grupo2_ao
            }
            "aq"->{
                return R.drawable.drawable_grupo2_aq
            }
            "ar"->{
                return R.drawable.drawable_grupo2_ar
            }
            "as"->{
                return R.drawable.drawable_grupo2_as
            }
            "at"->{
                return R.drawable.drawable_grupo2_at
            }
            "au"->{
                return R.drawable.drawable_grupo2_au
            }
            "aw"->{
                return R.drawable.drawable_grupo2_aw
            }
            "ax"->{
                return R.drawable.drawable_grupo2_ax
            }
            "az"->{
                return R.drawable.drawable_grupo2_az
            }
            "ba"->{
                return R.drawable.drawable_grupo2_ba
            }
            "bb"->{
                return R.drawable.drawable_grupo2_bb
            }
            "bd"->{
                return R.drawable.drawable_grupo2_bd
            }
            "be"->{
                return R.drawable.drawable_grupo2_be
            }
            "bf"->{
                return R.drawable.drawable_grupo2_bf
            }
            "bg"->{
                return R.drawable.drawable_grupo2_bg
            }
            "bh"->{
                return R.drawable.drawable_grupo2_bh
            }
            "bi"->{
                return R.drawable.drawable_grupo2_bi
            }
            "bj"->{
                return R.drawable.drawable_grupo2_bj
            }
            "bl"->{
                return R.drawable.drawable_grupo2_bl
            }
            "bm"->{
                return R.drawable.drawable_grupo2_bm
            }
            "bn"->{
                return R.drawable.drawable_grupo2_bn
            }
            "bo"->{
                return R.drawable.drawable_grupo2_bo
            }
            "bq"->{
                return R.drawable.drawable_grupo2_bq
            }
            "br"->{
                return R.drawable.drawable_grupo2_br
            }
            "bs"->{
                return R.drawable.drawable_grupo2_bs
            }
            "bt"->{
                return R.drawable.drawable_grupo2_bt
            }
            "bv"->{
                return R.drawable.drawable_grupo2_bv
            }
            "bw"->{
                return R.drawable.drawable_grupo2_bw
            }
            "by"->{
                return R.drawable.drawable_grupo2_by
            }
            "bz"->{
                return R.drawable.drawable_grupo2_bz
            }
            "ca"->{
                return R.drawable.drawable_grupo2_ca
            }
            "cc"->{
                return R.drawable.drawable_grupo2_cc
            }
            "cd"->{
                return R.drawable.drawable_grupo2_cd
            }
            "cf"->{
                return R.drawable.drawable_grupo2_cf
            }
            "cg"->{
                return R.drawable.drawable_grupo2_cg
            }
            "ch"->{
                return R.drawable.drawable_grupo2_ch
            }
            "ci"->{
                return R.drawable.drawable_grupo2_ci
            }
            "ck"->{
                return R.drawable.drawable_grupo2_ck
            }
            "cl"->{
                return R.drawable.drawable_grupo2_cl
            }
            "cm"->{
                return R.drawable.drawable_grupo2_cm
            }
            "cn"->{
                return R.drawable.drawable_grupo2_cn
            }
            "co"->{
                return R.drawable.drawable_grupo2_co
            }
            "cr"->{
                return R.drawable.drawable_grupo2_cr
            }
            "cu"->{
                return R.drawable.drawable_grupo2_cu
            }
            "cv"->{
                return R.drawable.drawable_grupo2_cv
            }
            "cw"->{
                return R.drawable.drawable_grupo2_cw
            }
            "cx"->{
                return R.drawable.drawable_grupo2_cx
            }
            "cy"->{
                return R.drawable.drawable_grupo2_cy
            }
            "cz"->{
                return R.drawable.drawable_grupo2_cz
            }
            "de"->{
                return R.drawable.drawable_grupo2_de
            }
            "dj"->{
                return R.drawable.drawable_grupo2_dj
            }
            "dk"->{
                return R.drawable.drawable_grupo2_dk
            }
            "dm"->{
                return R.drawable.drawable_grupo2_dm
            }
            "do"->{
                return R.drawable.drawable_grupo2_do
            }
            "dz"->{
                return R.drawable.drawable_grupo2_dz
            }
            "ec"->{
                return R.drawable.drawable_grupo2_ec
            }
            "ee"->{
                return R.drawable.drawable_grupo2_ee
            }
            "eg"->{
                return R.drawable.drawable_grupo2_eg
            }
            "eh"->{
                return R.drawable.drawable_grupo2_eh
            }
            "er"->{
                return R.drawable.drawable_grupo2_er
            }
            "es"->{
                return R.drawable.drawable_grupo2_es
            }
            "et"->{
                return R.drawable.drawable_grupo2_et
            }
            "fi"->{
                return R.drawable.drawable_grupo2_fi
            }
            "fj"->{
                return R.drawable.drawable_grupo2_fj
            }
            "fk"->{
                return R.drawable.drawable_grupo2_fk
            }
            "fm"->{
                return R.drawable.drawable_grupo2_fm
            }
            "fo"->{
                return R.drawable.drawable_grupo2_fo
            }
            "fr"->{
                return R.drawable.drawable_grupo2_fr
            }
            "ga"->{
                return R.drawable.drawable_grupo2_ga
            }
            "gb"->{
                return R.drawable.drawable_grupo2_gb
            }
            "gd"->{
                return R.drawable.drawable_grupo2_gd
            }
            "ge"->{
                return R.drawable.drawable_grupo2_ge
            }
            "gf"->{
                return R.drawable.drawable_grupo2_gf
            }
            "gg"->{
                return R.drawable.drawable_grupo2_gg
            }
            "gh"->{
                return R.drawable.drawable_grupo2_gh
            }
            "gi"->{
                return R.drawable.drawable_grupo2_gi
            }
            "gl"->{
                return R.drawable.drawable_grupo2_gl
            }
            "gm"->{
                return R.drawable.drawable_grupo2_gm
            }
            "gn"->{
                return R.drawable.drawable_grupo2_gn
            }
            "gp"->{
                return R.drawable.drawable_grupo2_gp
            }
            "gq"->{
                return R.drawable.drawable_grupo2_gq
            }
            "gr"->{
                return R.drawable.drawable_grupo2_gr
            }
            "gs"->{
                return R.drawable.drawable_grupo2_gs
            }
            "gt"->{
                return R.drawable.drawable_grupo2_gt
            }
            "gu"->{
                return R.drawable.drawable_grupo2_gu
            }
            "gw"->{
                return R.drawable.drawable_grupo2_gw
            }
            "gy"->{
                return R.drawable.drawable_grupo2_gy
            }
            "hk"->{
                return R.drawable.drawable_grupo2_hk
            }
            "hm"->{
                return R.drawable.drawable_grupo2_hm
            }
            "hn"->{
                return R.drawable.drawable_grupo2_hn
            }
            "hr"->{
                return R.drawable.drawable_grupo2_hr
            }
            "ht"->{
                return R.drawable.drawable_grupo2_ht
            }
            "hu"->{
                return R.drawable.drawable_grupo2_hu
            }
            "id"->{
                return R.drawable.drawable_grupo2_id
            }
            "ie"->{
                return R.drawable.drawable_grupo2_ie
            }
            "il"->{
                return R.drawable.drawable_grupo2_il
            }
            "im"->{
                return R.drawable.drawable_grupo2_im
            }
            "in"->{
                return R.drawable.drawable_grupo2_in
            }
            "io"->{
                return R.drawable.drawable_grupo2_io
            }
            "iq"->{
                return R.drawable.drawable_grupo2_iq
            }
            "ir"->{
                return R.drawable.drawable_grupo2_ir
            }
            "is"->{
                return R.drawable.drawable_grupo2_is
            }
            "it"->{
                return R.drawable.drawable_grupo2_it
            }
            "je"->{
                return R.drawable.drawable_grupo2_je
            }
            "jm"->{
                return R.drawable.drawable_grupo2_jm
            }
            "jo"->{
                return R.drawable.drawable_grupo2_jo
            }
            "jp"->{
                return R.drawable.drawable_grupo2_jp
            }
            "ke"->{
                return R.drawable.drawable_grupo2_ke
            }
            "kg"->{
                return R.drawable.drawable_grupo2_kg
            }
            "kh"->{
                return R.drawable.drawable_grupo2_kh
            }
            "ki"->{
                return R.drawable.drawable_grupo2_ki
            }
            "km"->{
                return R.drawable.drawable_grupo2_km
            }
            "kn"->{
                return R.drawable.drawable_grupo2_kn
            }
            "kp"->{
                return R.drawable.drawable_grupo2_kp
            }
            "kr"->{
                return R.drawable.drawable_grupo2_kr
            }
            "kw"->{
                return R.drawable.drawable_grupo2_kw
            }
            "ky"->{
                return R.drawable.drawable_grupo2_ky
            }
            "kz"->{
                return R.drawable.drawable_grupo2_kz
            }
            "la"->{
                return R.drawable.drawable_grupo2_la
            }
            "lb"->{
                return R.drawable.drawable_grupo2_lb
            }
            "lc"->{
                return R.drawable.drawable_grupo2_lc
            }
            "li"->{
                return R.drawable.drawable_grupo2_li
            }
            "lk"->{
                return R.drawable.drawable_grupo2_lk
            }
            "lr"->{
                return R.drawable.drawable_grupo2_lr
            }
            "ls"->{
                return R.drawable.drawable_grupo2_ls
            }
            "lt"->{
                return R.drawable.drawable_grupo2_lt
            }
            "lu"->{
                return R.drawable.drawable_grupo2_lu
            }
            "lv"->{
                return R.drawable.drawable_grupo2_lv
            }
            "ly"->{
                return R.drawable.drawable_grupo2_ly
            }
            "ma"->{
                return R.drawable.drawable_grupo2_ma
            }
            "mc"->{
                return R.drawable.drawable_grupo2_mc
            }
            "md"->{
                return R.drawable.drawable_grupo2_md
            }
            "me"->{
                return R.drawable.drawable_grupo2_me
            }
            "mf"->{
                return R.drawable.drawable_grupo2_mf
            }
            "mg"->{
                return R.drawable.drawable_grupo2_mg
            }
            "mh"->{
                return R.drawable.drawable_grupo2_mh
            }
            "mk"->{
                return R.drawable.drawable_grupo2_mk
            }
            "ml"->{
                return R.drawable.drawable_grupo2_ml
            }
            "mm"->{
                return R.drawable.drawable_grupo2_mm
            }
            "mn"->{
                return R.drawable.drawable_grupo2_mn
            }
            "mo"->{
                return R.drawable.drawable_grupo2_mo
            }
            "mp"->{
                return R.drawable.drawable_grupo2_mp
            }
            "mq"->{
                return R.drawable.drawable_grupo2_mq
            }
            "mr"->{
                return R.drawable.drawable_grupo2_mr
            }
            "ms"->{
                return R.drawable.drawable_grupo2_ms
            }
            "mt"->{
                return R.drawable.drawable_grupo2_mt
            }
            "mu"->{
                return R.drawable.drawable_grupo2_mu
            }
            "mv"->{
                return R.drawable.drawable_grupo2_mv
            }
            "mw"->{
                return R.drawable.drawable_grupo2_mw
            }
            "mx"->{
                return R.drawable.drawable_grupo2_mx
            }
            "my"->{
                return R.drawable.drawable_grupo2_my
            }
            "mz"->{
                return R.drawable.drawable_grupo2_mz
            }
            "na"->{
                return R.drawable.drawable_grupo2_na
            }
            "nc"->{
                return R.drawable.drawable_grupo2_nc
            }
            "ne"->{
                return R.drawable.drawable_grupo2_ne
            }
            "nf"->{
                return R.drawable.drawable_grupo2_nf
            }
            "ng"->{
                return R.drawable.drawable_grupo2_ng
            }
            "ni"->{
                return R.drawable.drawable_grupo2_ni
            }
            "nl"->{
                return R.drawable.drawable_grupo2_nl
            }
            "no"->{
                return R.drawable.drawable_grupo2_no
            }
            "np"->{
                return R.drawable.drawable_grupo2_np
            }
            "nr"->{
                return R.drawable.drawable_grupo2_nr
            }
            "nu"->{
                return R.drawable.drawable_grupo2_nu
            }
            "nz"->{
                return R.drawable.drawable_grupo2_nz
            }
            "om"->{
                return R.drawable.drawable_grupo2_om
            }
            "pa"->{
                return R.drawable.drawable_grupo2_pa
            }
            "pe"->{
                return R.drawable.drawable_grupo2_pe
            }
            "pf"->{
                return R.drawable.drawable_grupo2_pf
            }
            "pg"->{
                return R.drawable.drawable_grupo2_pg
            }
            "ph"->{
                return R.drawable.drawable_grupo2_ph
            }
            "pk"->{
                return R.drawable.drawable_grupo2_pk
            }
            "pl"->{
                return R.drawable.drawable_grupo2_pl
            }
            "pm"->{
                return R.drawable.drawable_grupo2_pm
            }
            "pn"->{
                return R.drawable.drawable_grupo2_pn
            }
            "pr"->{
                return R.drawable.drawable_grupo2_pr
            }
            "ps"->{
                return R.drawable.drawable_grupo2_ps
            }
            "pt"->{
                return R.drawable.drawable_grupo2_pt
            }
            "pw"->{
                return R.drawable.drawable_grupo2_pw
            }
            "py"->{
                return R.drawable.drawable_grupo2_py
            }
            "qa"->{
                return R.drawable.drawable_grupo2_qa
            }
            "re"->{
                return R.drawable.drawable_grupo2_re
            }
            "ro"->{
                return R.drawable.drawable_grupo2_ro
            }
            "rs"->{
                return R.drawable.drawable_grupo2_rs
            }
            "ru"->{
                return R.drawable.drawable_grupo2_ru
            }
            "rw"->{
                return R.drawable.drawable_grupo2_rw
            }
            "sa"->{
                return R.drawable.drawable_grupo2_sa
            }
            "sb"->{
                return R.drawable.drawable_grupo2_sb
            }
            "sc"->{
                return R.drawable.drawable_grupo2_sc
            }
            "sd"->{
                return R.drawable.drawable_grupo2_sd
            }
            "se"->{
                return R.drawable.drawable_grupo2_se
            }
            "sg"->{
                return R.drawable.drawable_grupo2_sg
            }
            "sh"->{
                return R.drawable.drawable_grupo2_sh
            }
            "si"->{
                return R.drawable.drawable_grupo2_si
            }
            "sj"->{
                return R.drawable.drawable_grupo2_sj
            }
            "sk"->{
                return R.drawable.drawable_grupo2_sk
            }
            "sl"->{
                return R.drawable.drawable_grupo2_sl
            }
            "sm"->{
                return R.drawable.drawable_grupo2_sm
            }
            "sn"->{
                return R.drawable.drawable_grupo2_sn
            }
            "so"->{
                return R.drawable.drawable_grupo2_so
            }
            "sr"->{
                return R.drawable.drawable_grupo2_sr
            }
            "ss"->{
                return R.drawable.drawable_grupo2_ss
            }
            "st"->{
                return R.drawable.drawable_grupo2_st
            }
            "sv"->{
                return R.drawable.drawable_grupo2_sv
            }
            "sx"->{
                return R.drawable.drawable_grupo2_sx
            }
            "sy"->{
                return R.drawable.drawable_grupo2_sy
            }
            "sz"->{
                return R.drawable.drawable_grupo2_sz
            }
            "tc"->{
                return R.drawable.drawable_grupo2_tc
            }
            "td"->{
                return R.drawable.drawable_grupo2_td
            }
            "tf"->{
                return R.drawable.drawable_grupo2_tf
            }
            "tg"->{
                return R.drawable.drawable_grupo2_tg
            }
            "th"->{
                return R.drawable.drawable_grupo2_th
            }
            "tj"->{
                return R.drawable.drawable_grupo2_tj
            }
            "tk"->{
                return R.drawable.drawable_grupo2_tk
            }
            "tl"->{
                return R.drawable.drawable_grupo2_tl
            }
            "tm"->{
                return R.drawable.drawable_grupo2_tm
            }
            "tn"->{
                return R.drawable.drawable_grupo2_tn
            }
            "to"->{
                return R.drawable.drawable_grupo2_to
            }
            "tr"->{
                return R.drawable.drawable_grupo2_tr
            }
            "tt"->{
                return R.drawable.drawable_grupo2_tt
            }
            "tv"->{
                return R.drawable.drawable_grupo2_tv
            }
            "tw"->{
                return R.drawable.drawable_grupo2_tw
            }
            "tz"->{
                return R.drawable.drawable_grupo2_tz
            }
            "ua"->{
                return R.drawable.drawable_grupo2_ua
            }
            "ug"->{
                return R.drawable.drawable_grupo2_ug
            }
            "um"->{
                return R.drawable.drawable_grupo2_um
            }
            "us"->{
                return R.drawable.drawable_grupo2_us
            }
            "uy"->{
                return R.drawable.drawable_grupo2_uy
            }
            "uz"->{
                return R.drawable.drawable_grupo2_uz
            }
            "va"->{
                return R.drawable.drawable_grupo2_va
            }
            "vc"->{
                return R.drawable.drawable_grupo2_vc
            }
            "ve"->{
                return R.drawable.drawable_grupo2_ve
            }
            "vg"->{
                return R.drawable.drawable_grupo2_vg
            }
            "vi"->{
                return R.drawable.drawable_grupo2_vi
            }
            "vn"->{
                return R.drawable.drawable_grupo2_vn
            }
            "vu"->{
                return R.drawable.drawable_grupo2_vu
            }
            "wf"->{
                return R.drawable.drawable_grupo2_wf
            }
            "ws"->{
                return R.drawable.drawable_grupo2_ws
            }
            "xk"->{
                return R.drawable.drawable_grupo2_xk
            }
            "ye"->{
                return R.drawable.drawable_grupo2_ye
            }
            "yt"->{
                return R.drawable.drawable_grupo2_yt
            }
            "za"->{
                return R.drawable.drawable_grupo2_za
            }
            "zm"->{
                return R.drawable.drawable_grupo2_zm
            }
            "zw"->{
                return R.drawable.drawable_grupo2_zw
            }
            else->{
                return R.drawable.naciones_unidas
            }
        }
    }



}

