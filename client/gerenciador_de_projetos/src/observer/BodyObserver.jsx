import { useEffect } from 'react';

const BodyObserver = ({ onBodyClassChange }) => {
  const observeBodyClass = () => {
    const targetNode = document.body;
    const isDarkMode = targetNode.classList.contains('dark');
    onBodyClassChange(isDarkMode);

    const observer = new MutationObserver((mutationsList) => {
      for (const mutation of mutationsList) {
        if (mutation.attributeName === 'class') {
          const isDarkMode = targetNode.classList.contains('dark');
          onBodyClassChange(isDarkMode);
        }
      }
    });

    const config = { attributes: true };

    observer.observe(targetNode, config);

    return () => {
      observer.disconnect();
    };
  };

  useEffect(() => {
    observeBodyClass();
  }, [onBodyClassChange]);

  return null;
};

export default BodyObserver;
