// src/components/OficiosSlider.jsx
import { Swiper, SwiperSlide } from 'swiper/react';
import { A11y, Navigation } from 'swiper/modules';

import 'swiper/css';
import 'swiper/css/navigation';
import '../../styles/components/OficiosSlider.css'; 

import oficio1 from '../../assets/oficios/oficio1.jpg';
import oficio2 from '../../assets/oficios/oficio2.jpg';
import oficio3 from '../../assets/oficios/oficio3.jpg';
import oficio4 from '../../assets/oficios/oficio4.jpg';
import oficio5 from '../../assets/oficios/oficio5.jpg';
import oficio6 from '../../assets/oficios/oficio6.jpg';

const oficiosData = [
  { id: 1, titulo: 'Electricista', img: oficio1 },
  { id: 2, titulo: 'Plomero', img: oficio2 },
  { id: 3, titulo: 'Carpintero', img: oficio3 },
  { id: 4, titulo: 'Pintor', img: oficio4 },
  { id: 5, titulo: 'Jardinero', img: oficio5 },
  { id: 6, titulo: 'Mecánico', img: oficio6 },
];

const OficiosSlider = () => (
    <section className="section">
        <h2 className="oficios-title">Oficios más buscados 🔧</h2>
        <Swiper
            slidesPerView={1}
            spaceBetween={12}
            breakpoints={{
                640: { slidesPerView: 2, spaceBetween: 12 },
                768: { slidesPerView: 3, spaceBetween: 16 },
                1024: { slidesPerView: 4, spaceBetween: 20 },
            }}
            navigation
            modules={[Navigation, A11y]}
            className="img img--poster swiper--oficios"
            >
        {oficiosData.map(({ id, titulo, img }) => (
            <SwiperSlide key={id}>
            <div className="oficio-card">
                <img src={img} alt={titulo}
                width="180"
                height="320"
                className="oficio-img" />
                <h3 className="oficio-title">{titulo}</h3>
            </div>
            </SwiperSlide>
        ))}
        </Swiper>
  </section>
);

export default OficiosSlider;
