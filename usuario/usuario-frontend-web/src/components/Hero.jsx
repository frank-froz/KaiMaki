// src/components/Hero.jsx
import { Swiper, SwiperSlide } from 'swiper/react';
import { Autoplay, EffectCoverflow, Navigation, Pagination } from 'swiper/modules';

import 'swiper/css';
import 'swiper/css/effect-coverflow';
import 'swiper/css/pagination';
import 'swiper/css/navigation';

import '../css/Hero.css'; 

import img1 from '../assets/hero1.jpg';
import img2 from '../assets/hero2.jpeg';
import img3 from '../assets/hero3.jpg';
import img4 from '../assets/hero4.jpg'

const slidesData = [
  {
    id: 1,
    title: 'Encuentra al profesional ideal',
    description: 'Explora miles de servicios en un solo lugar.',
    image: img1,
  },
  {
    id: 2,
    title: 'Solicita con un clic',
    description: 'Pide el servicio que necesitas rápidamente.',
    image: img2,
  },
  {
    id: 3,
    title: 'Califica y recomienda',
    description: 'Comparte tu experiencia con otros usuarios.',
    image: img3,
  },
  {
    id: 4,
    title: 'Califica y recomienda',
    description: 'Comparte tu experiencia con otros usuarios.',
    image: img4,
  },
];

const Hero = () => {
  return (
    <Swiper
    effect="coverflow"
    grabCursor={true}
    centeredSlides={true}
    slidesPerView={'auto'}
    autoplay={{ delay: 5000 }}
    pagination={{ clickable: true }}
    navigation
    loop
    modules={[Autoplay, EffectCoverflow, Pagination, Navigation]}
    >
    {slidesData.map(({ id, title, description, image }) => (
        <SwiperSlide key={id}>
        <section className="hero">
            <div className="overlay" />
            <img src={image} alt={title} />
            <div className="slide-text">
            <h2 className="hero__title">{title}</h2>
            <p className="hero__paragraph">{description}</p>
            </div>
        </section>
        </SwiperSlide>
    ))}
    </Swiper>

  );
};

export default Hero;
