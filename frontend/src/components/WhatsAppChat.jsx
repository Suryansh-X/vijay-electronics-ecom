import React, { useState } from 'react';
import { MessageCircle, X } from 'lucide-react';
import { motion, AnimatePresence } from 'framer-motion';

function WhatsAppChat() {
  const [isOpen, setIsOpen] = useState(false);
  const [message, setMessage] = useState('');
  const [selectedProduct, setSelectedProduct] = useState('');

  const phoneNumber = '919876898832'; // Replace with your WhatsApp number
  const shopName = 'Vijay Electronics';

  const handleSendMessage = () => {
    const fullMessage = `Hi, I found ${selectedProduct || 'a product'} on your website. Can you tell me more about this?`;
    const encodedMessage = encodeURIComponent(fullMessage);
    const whatsappUrl = `https://wa.me/${phoneNumber}?text=${encodedMessage}`;
    window.open(whatsappUrl, '_blank');
    setIsOpen(false);
    setMessage('');
  };

  const quickMessages = [
    'I found a Samsung TV on your site. Can you tell me more?',
    'What\'s the warranty on washing machines?',
    'Do you have AC in stock?',
    'Can I get a discount?',
    'What\'s the delivery time?'
  ];

  return (
    <div className="fixed bottom-6 right-6 z-40">
      {/* Chat Button */}
      <AnimatePresence>
        {!isOpen && (
          <motion.button
            initial={{ scale: 0 }}
            animate={{ scale: 1 }}
            exit={{ scale: 0 }}
            whileHover={{ scale: 1.1 }}
            whileTap={{ scale: 0.95 }}
            onClick={() => setIsOpen(true)}
            className="bg-green-500 hover:bg-green-600 text-white rounded-full p-4 shadow-lg flex items-center gap-2 font-bold"
          >
            <MessageCircle size={24} />
            <span className="hidden sm:inline">Chat</span>
          </motion.button>
        )}
      </AnimatePresence>

      {/* Chat Box */}
      <AnimatePresence>
        {isOpen && (
          <motion.div
            initial={{ opacity: 0, scale: 0.8, y: 20 }}
            animate={{ opacity: 1, scale: 1, y: 0 }}
            exit={{ opacity: 0, scale: 0.8, y: 20 }}
            className="absolute bottom-20 right-0 bg-white rounded-lg shadow-2xl w-80 sm:w-96 border border-gray-200"
          >
            {/* Header */}
            <div className="bg-gradient-to-r from-green-500 to-green-600 text-white p-4 flex justify-between items-center rounded-t-lg">
              <div className="flex items-center gap-2">
                <MessageCircle size={20} />
                <div>
                  <h3 className="font-bold">{shopName}</h3>
                  <p className="text-xs opacity-90">Typically replies instantly</p>
                </div>
              </div>
              <button
                onClick={() => setIsOpen(false)}
                className="hover:bg-green-700 p-1 rounded transition"
              >
                <X size={20} />
              </button>
            </div>

            {/* Chat Content */}
            <div className="p-4 space-y-3 max-h-64 overflow-y-auto">
              <div className="text-sm text-gray-700">
                👋 Hi! How can we help you today?
              </div>

              {/* Quick Messages */}
              <div className="space-y-2">
                <p className="text-xs text-gray-500 font-semibold">Popular questions:</p>
                {quickMessages.map((msg, idx) => (
                  <button
                    key={idx}
                    onClick={() => {
                      setMessage(msg);
                      handleSendMessage();
                    }}
                    className="w-full text-left bg-gray-100 hover:bg-gray-200 p-2 rounded text-sm text-gray-700 transition"
                  >
                    💬 {msg}
                  </button>
                ))}
              </div>
            </div>

            {/* Input */}
            <div className="border-t p-3 space-y-2">
              <input
                type="text"
                placeholder="Type your message..."
                value={message}
                onChange={(e) => setMessage(e.target.value)}
                onKeyPress={(e) => e.key === 'Enter' && handleSendMessage()}
                className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-green-500 text-sm"
              />
              <button
                onClick={handleSendMessage}
                disabled={!message.trim()}
                className="w-full bg-green-500 hover:bg-green-600 disabled:bg-gray-300 text-white py-2 rounded-lg font-bold transition text-sm"
              >
                Send on WhatsApp
              </button>
            </div>

            {/* Footer */}
            <div className="bg-gray-50 p-2 text-center text-xs text-gray-500 rounded-b-lg">
              📱 +91 {phoneNumber.slice(-10)}
            </div>
          </motion.div>
        )}
      </AnimatePresence>
    </div>
  );
}

export default WhatsAppChat;
