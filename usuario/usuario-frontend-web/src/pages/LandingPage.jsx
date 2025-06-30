import React from 'react';
import Header from '../components/Header';
import Hero from '../components/Hero';
import ThreeSteps from '../components/ThreeSteps';
import OficiosSlider from '../components/OficiosSlider';

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
