import React from 'react';
import Header from '../../components/layout/Header';
import Hero from '../../components/layout/Hero';
import ThreeSteps from '../../components/home/ThreeSteps';
import OficiosSlider from '../../components/home/OficiosSlider';

const LandingPage = () => {
  return (
    <div className="bg-[#333333] text-white min-h-screen flex flex-col">
      <Header isLoggedIn={false} />
      <Hero />
      <OficiosSlider /> 
      <ThreeSteps />
    </div>
  );
};

export default LandingPage;
